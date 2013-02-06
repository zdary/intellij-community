package org.hanuna.gitalk;

import org.hanuna.gitalk.graph.Graph;
import org.hanuna.gitalk.graph.elements.Branch;
import org.hanuna.gitalk.graph.elements.Edge;
import org.hanuna.gitalk.graph.elements.Node;
import org.hanuna.gitalk.graph.elements.NodeRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author erokhins
 */
public class GraphStrUtils {

    public static String toStr(Branch branch) {
        if (branch.getUpCommitHash() == branch.getDownCommitHash()) {
            return branch.getUpCommitHash().toStrHash();
        } else {
            return branch.getUpCommitHash().toStrHash() + '#' + branch.getDownCommitHash().toStrHash();
        }
    }

    /**
     *
     * @return
     * example:
     * a0:a1:USUAL:a0
     * up:down:type:branch
     */
    public static String toStr(Edge edge) {
        StringBuilder s = new StringBuilder();
        s.append(edge.getUpNode().getCommitHash().toStrHash()).append(":");
        s.append(edge.getDownNode().getCommitHash().toStrHash()).append(":");
        s.append(edge.getType()).append(":");
        s.append(toStr(edge.getBranch()));
        return s.toString();
    }

    public static String toStr(List<Edge> edges) {
        StringBuilder s = new StringBuilder();
        List<String> edgeStrings = new ArrayList<String>();
        for (Edge edge : edges) {
            edgeStrings.add(toStr(edge));
        }

        Collections.sort(edgeStrings);
        if (edgeStrings.size() > 0) {
            s.append(edgeStrings.get(0));
        }
        for (int i = 1; i < edges.size(); i++) {
            s.append(" ").append(edgeStrings.get(i));
        }
        return s.toString();
    }



    /**
     *
     * @return
     * example:
     * a0|-|-a0:a1:USUAL:a0|-COMMIT_NODE|-a0|-0
     *
     * explanation:
     * getCommitHash|-upEdges|-downEdges|-Type|-branch|-rowIndex
     *
     */

    public static String toStr(Node node) {
        StringBuilder s = new StringBuilder();
        s.append(node.getCommitHash().toStrHash()).append("|-");
        s.append(toStr(node.getUpEdges())).append("|-");
        s.append(toStr(node.getDownEdges())).append("|-");
        s.append(node.getType()).append("|-");
        s.append(toStr(node.getBranch())).append("|-");
        s.append(node.getRowIndex());
        return s.toString();
    }
    public static String toStr(NodeRow row) {
        StringBuilder s = new StringBuilder();
        List<Node> nodes = row.getNodes();
        if (nodes.size() > 0) {
            s.append(toStr(nodes.get(0)));
        }
        for (int i = 1; i < nodes.size(); i++) {
            s.append("\n   ").append(toStr(nodes.get(i)));
        }
        return s.toString();
    }

    /**
     *
     * @return textOfGraph in next format:
     * every row in separate line, if in row more that 1 node:
     * ...
     * first node text row
     *      next node text row
     *      next node text row
     * next row ...
     */

    public static String toStr(Graph graph) {
        StringBuilder s = new StringBuilder();
        List<NodeRow> rows = graph.getNodeRows();
        if (rows.size() > 0)  {
            s.append(toStr(rows.get(0)));
        }
        for (int i = 1; i < rows.size(); i++) {
            s.append("\n").append(toStr(rows.get(i)));
        }
        return s.toString();
    }

     /*
    public static String toShortStr(@NotNull Node node) {
        return node.getCommitHash().toStrHash() + ":" + node.getRowIndex();
    }


    public static String toStr(@Nullable GraphFragment fragment) {
        if (fragment == null) {
            return "null";
        }
        StringBuilder s = new StringBuilder();
        s.append(toShortStr(fragment.getUpNode())).append("|-");

        List<String> intermediateNodeStr = new ArrayList<String>();
        for (Node intermediateNode : fragment.getIntermediateNodes()) {
            intermediateNodeStr.add(toShortStr(intermediateNode));
        }
        Collections.sort(intermediateNodeStr);
        for (int i = 0; i < intermediateNodeStr.size(); i++) {
            if (i > 0) {
                s.append(" ");
            }
            s.append(intermediateNodeStr.get(i));
        }
        s.append("|-").append(toShortStr(fragment.getDownNode()));
        return s.toString();
    }
                         */


}
