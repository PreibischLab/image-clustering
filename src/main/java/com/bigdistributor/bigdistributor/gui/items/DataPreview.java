package net.preibisch.bigdistributor.gui.items;

import java.util.Arrays;
import java.util.List;

import net.imglib2.util.Util;
import net.preibisch.bigdistributor.algorithm.blockmanagement.blockinfo.BasicBlockInfoGenerator;
import net.preibisch.bigdistributor.gui.GUIConfig;
import net.preibisch.bigdistributor.gui.blocks.GraphicBlocksManager;

public class DataPreview extends Object {
	private static long[] dims;
	private static long[] blocksSizes;
	private static long overlap = 10;
	private static List<BlockPreview> blocksPreview;
	private static int previewPreferedHeight ;

	public static long[] getDims() {
		return dims;
	}
	
	public static List<BlockPreview> getBlocksPreview() {
		return blocksPreview;
	}

	public static long[] getBlocksSizes() {
		return blocksSizes;
	}

	public static void setBlockSize(int position, long blockSize) {
		DataPreview.blocksSizes[position] = blockSize;
	}

	public static void setOverlap(long overlap) {
		DataPreview.overlap = overlap;
	}

	public static long getOverlap() {
		return overlap;
	}

	public static int getPreviewPreferedHeight() {
		return previewPreferedHeight;
	}

	public static void generateBlocks() {
		DataPreview.blocksPreview = GraphicBlocksManager.generateBlocks(dims, blocksSizes, overlap);
	}

	public DataPreview(long[] dims,long overlap,long[] blockSize) {
		this(dims, overlap, blockSize, GUIConfig.PREVIEW_PREFERED_HEIGHT);
	}
	
	public DataPreview(long[] dims,long overlap,long[] blockSize,int previewPreferedHeight) {
		DataPreview.dims = dims;
		DataPreview.overlap = overlap;
		DataPreview.blocksSizes = blockSize;
		DataPreview.previewPreferedHeight = previewPreferedHeight;
		generateBlocks();
	}
	
	public static void fromFile(long[] dims) {
		DataPreview.dims = dims;
		DataPreview.previewPreferedHeight = GUIConfig.PREVIEW_PREFERED_HEIGHT;
		long[] blocksSizes = new long[dims.length];
		Arrays.fill(blocksSizes, BasicBlockInfoGenerator.BLOCK_SIZE);
		DataPreview.blocksSizes = blocksSizes;
		System.out.println(DataPreview.str());
		generateBlocks();
	}
	
	public static String str() {	
		return "DataPreview: Dims = " +Util.printCoordinates(dims) + " | prefered height: "+previewPreferedHeight+ " | Block size: "+ blocksSizes ;
	}

}
