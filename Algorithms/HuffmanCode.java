import java.util.PriorityQueue;


abstract class HuffmanTree implements Comparable<HuffmanTree> {

	public final int frequency; // the frequency of this tree

	public HuffmanTree(int freq) {
		frequency = freq;
	}

	// compares on the frequency
	public int compareTo(HuffmanTree tree) {
		return frequency - tree.frequency;
	}
}


class HuffmanLeaf extends HuffmanTree {

	public final char value; // the character this leaf represents

	public HuffmanLeaf(int freq, char val) {
		super(freq);
		value = val;
	}
}

public class HuffmanCode {

	public static int step = 0; // the frequency of this tree
	public static int stepone = 0; // the frequency of this tree

	static class HuffmanNode extends HuffmanTree {

		public final HuffmanTree left, right; // subtrees

		public HuffmanNode(HuffmanTree l, HuffmanTree r) {
			super(l.frequency + r.frequency);
			left = l;
			right = r;
		}
	}

	// input is an array of frequencies, indexed by character code
	public static HuffmanTree buildTree(int[] charFreqs) {

		PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
//        PQueue<HuffmanTree> trees = new PQueue<HuffmanTree>();
		// initially, we have a forest of leaves
		// one for each non-empty character
		for (int i = 0; i < charFreqs.length; i++) {
			if (charFreqs[i] > 0) {
				stepone++;
				trees.offer(new HuffmanLeaf(charFreqs[i], (char) i));

			}
		}

//        assert trees.size() > 0;
		// loop until there is only one tree left
		while (trees.size() > 1) {

			// two trees with least frequency
			HuffmanTree a = trees.poll();
			HuffmanTree b = trees.poll();

			// put into new node and re-insert into queue
			trees.offer(new HuffmanNode(a, b));
		}
		return trees.poll();
	}
	static String codedString = "";

	public static void printCodes(HuffmanTree tree, StringBuffer prefix) {
		++step;
		HuffmanLeaf leaf = null;
//        assert tree != null;
		if (tree instanceof HuffmanLeaf) {
			leaf = (HuffmanLeaf) tree;

			// print out character, frequency, and code for this leaf (which is just the prefix)
			System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);
			codedString += prefix + " ";
		} else if (tree instanceof HuffmanNode) {

			HuffmanNode node = (HuffmanNode) tree;

			// traverse left
			prefix.append('0');

			printCodes(node.left, prefix);
			prefix.deleteCharAt(prefix.length() - 1);

			// traverse right
			prefix.append('1');
			printCodes(node.right, prefix);
			prefix.deleteCharAt(prefix.length() - 1);
		}


	}

	public static void main(String[] args) {

		String test = "this is an example of a Huffman tree";

		// we will assume that all our characters will have
		int[] charFreqs = new int[256];
		// read each character and record the frequencies
		for (char c : test.toCharArray()) {
			charFreqs[c]++;
		}
		// build tree
		HuffmanTree tree = buildTree(charFreqs);

		// print out results
		System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
		printCodes(tree, new StringBuffer());
		System.out.println("steps count : " + step);
	}
}
