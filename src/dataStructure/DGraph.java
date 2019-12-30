package dataStructure;

import java.util.Collection;
import java.util.HashMap;

public class DGraph implements graph{
	
	HashMap <Integer, HashMap<Integer, edge_data>> edgeMap = new HashMap <Integer, HashMap<Integer, edge_data>>();
	HashMap <Integer, node_data> nodeMap = new HashMap <Integer, node_data>();
	int McCounter = 0;
	int edgeCounter = 0;

	@Override
	public node_data getNode(int key) {
		return nodeMap.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		if (src==dest || nodeMap.get(src)==null || nodeMap.get(dest)==null)
			return null;
		if (edgeMap.get(nodeMap.get(src))!=null)
			return edgeMap.get(nodeMap.get(src)).get(dest);
		return null;
	}

	@Override
	public void addNode(node_data n) {
		nodeMap.put(n.getKey(), n);
	}

	@Override
	public void connect(int src, int dest, double w) {
		if (src==dest)
			throw new RuntimeException ("Error - src or dest does not exist");
		edge_data e = new Edge (src, dest, w);
		edgeMap.get(src).put(dest, e);
	}

	public Collection<node_data> getV() {
		return nodeMap.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return edgeMap.get(nodeMap.get(node_id)).values();
	}

	@Override
	public node_data removeNode(int key) {
		return null;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		McCounter++;
		if (edgeMap.get(src).get(dest)!=null) {
			Edge e = new Edge ();
			e = (Edge) edgeMap.get(src).get(dest);
			edgeMap.get(src).remove(dest);
			return e;
		}
		return null;
	}

	@Override
	public int nodeSize() {
		return nodeMap.size();
	}

	@Override
	public int edgeSize() {
		return this.edgeCounter;
	}

	@Override
	public int getMC() {
		return this.McCounter;
	}

}

