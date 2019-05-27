package main.java.net.preibisch.distribution.algorithm.controllers.metadata;

import java.util.Map;

import main.java.net.preibisch.distribution.algorithm.blockmanager.block.BasicBlockGenerator;
import main.java.net.preibisch.distribution.algorithm.blockmanager.block.BasicBlockInfo;
import main.java.net.preibisch.distribution.algorithm.blockmanager.block.BlockInfo;
import main.java.net.preibisch.distribution.algorithm.blockmanager.block.ComplexBloxGenerator;
import main.java.net.preibisch.distribution.algorithm.controllers.items.BlocksMetaData;
import main.java.net.preibisch.distribution.algorithm.controllers.items.callback.AbstractCallBack;
import net.imglib2.util.Util;
import net.preibisch.mvrecon.fiji.spimdata.boundingbox.BoundingBox;

public class MetadataGenerator {
		
	public static <T> BlocksMetaData genarateMetaData(long[] dims, long[] blockSize, long overlap,
			AbstractCallBack callback) throws Exception {
		
		if ( overlap == 0 )
		{
			final Map<Integer, BasicBlockInfo> blocks = BasicBlockGenerator.divideIntoBlocks(blockSize, dims, callback);
			return new BlocksMetaData(blocks, blockSize, dims,blocks.size());
		}
		else
		{
			throw new Exception("TODO ! ");
//			final Map<Integer, BlockInfo> blocks = ComplexBloxGenerator.generateBlocks(blockSize, dims, overlap, callback);	
//			return new BlocksMetaData(blocks, blockSize, dims,blocks.size());
		}
	}
	
	public static <T> BlocksMetaData genarateMetaData(BoundingBox bb, int[] blockSize) {
		return genarateMetaData(bb,Util.int2long(blockSize), 0);
	}
	
	
	public static <T> BlocksMetaData genarateMetaData(BoundingBox bb, long[] blockSize, int overlap) {
		
		if ( overlap == 0 )
		{
			final Map<Integer, BasicBlockInfo> blocks = BasicBlockGenerator.divideIntoBlocks(bb, blockSize);
			return new BlocksMetaData(blocks, blockSize, bb.getDimensions(1),blocks.size());
		}
		else
		{
			System.out.println("TODO");
			return null;
		}
		
	}

}