package gui;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import dataStructure.graph;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

public class GRAPH_GUI implements Runnable{
	
	private static DGraph graph;
	
	public static void save (String filename) throws IOException {
		FileOutputStream output = new FileOutputStream (filename+".ser");
		ObjectOutputStream obj = new ObjectOutputStream (output);
		if (graph == null)
			return;
		obj.writeObject(graph);
		obj.close();
		output.close();
	}
	
	public static void load (String filename) throws Exception {
		FileInputStream input = new FileInputStream (filename);
		ObjectInputStream obj = new ObjectInputStream (input);
		DGraph dg = (DGraph) obj.readObject();
		StdDraw.clear();
		draw (dg);
		graph = dg;
		input.close();
		obj.close();
	}
	
	public static DGraph getGraph() {
		return graph;
	}
	
	private static void setScale (DGraph dg) {
		Collection <node_data> nodes = dg.getV();
		Iterator <node_data> i = nodes.iterator();
		double maxX, minX, maxY, minY;
		if (i.hasNext()) {
			Point3D p = i.next().getLocation();
			maxX = p.x();
			minX = p.x();
			maxY = p.y();
			minY = p.y();
		}
		else
			return;
		while (i.hasNext()) {
			Point3D p = i.next().getLocation();
			if (p.x()<minX)
				minX = p.x();
			else if (p.x()>maxX)
				maxX = p.x();
			if (p.y()<minY)
				minY = p.y();
			else if (p.y()>maxY)
				maxY = p.y();
		}
		Range rx = new Range (minX-20, maxX+20);
		Range ry = new Range (minY-20, maxY+20);
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());
	}

	public static void draw(graph g) {
		DGraph dg = (DGraph) g;
		StdDraw.setCanvasSize(500,500);
		setScale(dg);
		Collection <node_data> nodes = dg.getV();
		Iterator <node_data> i = nodes.iterator();
		StdDraw.setPenColor(Color.red);
		StdDraw.setPenRadius(0.05);
		while (i.hasNext()) {
			node_data nd = i.next();
			Point3D p = nd.getLocation();
			StdDraw.point(p.x(), p.y());
			String s = nd.getKey()+"";
			StdDraw.text(p.x()+3, p.y()+3, s);
		}
		StdDraw.setPenRadius(0.005);
		i = nodes.iterator();
		while (i.hasNext()) {
			node_data nd = i.next();
			Iterator <edge_data> ei = dg.getE(nd.getKey()).iterator();
			while (ei.hasNext()) {
				edge_data ed = ei.next();
				Point3D src = nd.getLocation();
				Point3D dest = dg.getNode(ed.getDest()).getLocation();
				double w = ed.getWeight();
				StdDraw.setPenColor(Color.black);
				StdDraw.line(src.x(), src.y(), dest.x(), dest.y());
				StdDraw.setPenColor(Color.blue);
				StdDraw.square(src.x()+(dest.x()-src.x())*0.9, src.y()+(dest.y()-src.y())*0.9, 0.5);
				StdDraw.text((dest.x()+src.x())/2, (dest.y()+src.y())/2, String.format("%.2f", w));
			}
		}
		graph = dg;
	}

	public static void main(String[] args) {
		
	}

	@Override
	public void run() {
		int mc = graph.getMC();
		while (true) {
			synchronized (graph) {
				if (mc<graph.getMC()) {
					StdDraw.clear();
					draw(graph);
				}
				else {
					try {
						Thread.sleep(1500);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
