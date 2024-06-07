package main;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("List size: ");
        int aCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> a = IntStream.range(0, aCount).mapToObj(i -> {
            try {
            	System.out.println("Number " + i + " : ");
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        List<Double> result = runningMedian(a);

        System.out.println(result);

        bufferedReader.close();

	}
	
	private static List<Double> runningMedian(List<Integer> a) {
        List<Double> medianList = new ArrayList<>();
        PriorityQueue<Integer> lowerHalf = new PriorityQueue<>(Collections.reverseOrder()); // max-heap
        PriorityQueue<Integer> upperHalf = new PriorityQueue<>(); // min-heap

        for (int num : a) {
            addNumber(num, lowerHalf, upperHalf);
            rebalanceHeaps(lowerHalf, upperHalf);
            medianList.add(getMedian(lowerHalf, upperHalf));
        }

        return medianList;
    }

    private static void addNumber(int num, PriorityQueue<Integer> lowerHalf, PriorityQueue<Integer> upperHalf) {
        if (lowerHalf.isEmpty() || num < lowerHalf.peek()) {
            lowerHalf.add(num);
        } else {
            upperHalf.add(num);
        }
    }

    private static void rebalanceHeaps(PriorityQueue<Integer> lowerHalf, PriorityQueue<Integer> upperHalf) {
        if (lowerHalf.size() > upperHalf.size() + 1) {
            upperHalf.add(lowerHalf.poll());
        } else if (upperHalf.size() > lowerHalf.size()) {
            lowerHalf.add(upperHalf.poll());
        }
    }

    private static double getMedian(PriorityQueue<Integer> lowerHalf, PriorityQueue<Integer> upperHalf) {
        if (lowerHalf.size() == upperHalf.size()) {
            return (lowerHalf.peek() + upperHalf.peek()) / 2.0;
        } else {
            return lowerHalf.peek();
        }
    }

}
