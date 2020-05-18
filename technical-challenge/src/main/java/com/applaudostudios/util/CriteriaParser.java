package com.applaudostudios.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class parse the custom search string into a deque of operator and a set
 * of search criteria
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
public class CriteriaParser {

	private static Map<String, Operator> ops;

	private static Pattern specCriteraRegex = Pattern.compile("^(\\w+?)("
			+ Arrays.stream(SearchOperation.SIMPLE_OPERATION_SET).map(String::valueOf).collect(Collectors.joining("|"))
			+ ")(\\p{Punct}?)([a-zA-Z0-9_\\-:.+]+?)(\\p{Punct}?)$");

	private enum Operator {
		OR(1), AND(2);

		final int precedence;

		Operator(int p) {
			precedence = p;
		}
	}

	static {
		Map<String, Operator> tempMap = new HashMap<>();
		tempMap.put("AND", Operator.AND);
		tempMap.put("OR", Operator.OR);
		tempMap.put("or", Operator.OR);
		tempMap.put("and", Operator.AND);

		ops = Collections.unmodifiableMap(tempMap);
	}

	private static boolean isHigerPrecedenceOperator(String currOp, String prevOp) {
		return (ops.containsKey(prevOp) && ops.get(prevOp).precedence >= ops.get(currOp).precedence);
	}

	/**
	 * Parse the search string to all operator and their search criteria and return
	 * a deque
	 *
	 * @param searchParam must not be null.
	 * @return a deque.
	 */
	public static Deque<Object> parse(String searchParam) {

		Deque<Object> output = new LinkedList<>();
		Deque<String> stack = new LinkedList<>();

		Arrays.stream(searchParam.split("\\s+")).forEach(token -> {
			if (ops.containsKey(token)) {
				while (!stack.isEmpty() && isHigerPrecedenceOperator(token, stack.peek())) {
					output.push(stack.pop().equalsIgnoreCase(SearchOperation.OR_OPERATOR) ? SearchOperation.OR_OPERATOR
							: SearchOperation.AND_OPERATOR);
				}
				stack.push(token.equalsIgnoreCase(SearchOperation.OR_OPERATOR) ? SearchOperation.OR_OPERATOR
						: SearchOperation.AND_OPERATOR);
			} else if (token.equals(SearchOperation.LEFT_PARANTHESIS)) {
				stack.push(SearchOperation.LEFT_PARANTHESIS);
			} else if (token.equals(SearchOperation.RIGHT_PARANTHESIS)) {
				while (!stack.peek().equals(SearchOperation.LEFT_PARANTHESIS)) {
					output.push(stack.pop());
				}
				stack.pop();
			} else {
				Matcher matcher = specCriteraRegex.matcher(token);
				while (matcher.find()) {
					output.push(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3),
							matcher.group(4), matcher.group(5)));
				}
			}
		});

		while (!stack.isEmpty()) {
			output.push(stack.pop());
		}

		return output;
	}

}
