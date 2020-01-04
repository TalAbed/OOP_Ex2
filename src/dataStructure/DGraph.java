package dataStructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DGraph implements graph{
	
	private HashMap <Integer, HashMap<Integer, edge_data>> edgeMap = new HashMap <Integer, HashMap<Integer, edge_data>>();
	private HashMap <Integer, node_data> nodeMap = new HashMap <Integer, node_data>();
	private int McCounter = 0;
	private int edgeCounter = 0;

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
		boolean b = true;
		if (src==dest) {
			System.out.println("src and dest are equals");
			b = false;
		}
		if (b || (!(nodeMap.get(src)==null) || !(nodeMap.get(dest)==null))) {
			if (edgeMap.containsKey(src)) {
				if (edgeMap.get(src).get(dest) != null)
					throw new RuntimeException ("this edge is already exist");
				else {
					edge_data ed = new Edge (src, dest, w);
					this.edgeMap.get(src).put(dest, ed);
					McCounter++;
					edgeCounter++;
				}
			}
			else if (b)
				throw new RuntimeException ("src or dest is not exist");
		}
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
		node_data n1 = this.getNode(key);
		if (n1!=null) {
			for (Iterator<node_data> it = this.getV().iterator();it.hasNext();) {
				node_data n2 = (node_data) it.next();
				this.removeEdge(n2.getKey(), key);
			}
			if (edgeMap.get(key)!=null) {
				edgeCounter -= edgeMap.get(key).size();
				McCounter += edgeMap.get(key).size();
			}
			nodeMap.remove(key);
			edgeMap.remove(key);
			McCounter++;
		}
		return n1;
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

