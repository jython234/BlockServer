package org.blockserver.net.protocol.pe.raknet;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.blockserver.net.protocol.pe.PocketProtocolConstants;
import org.blockserver.utils.Utils;

public class RaknetReceivedCustomPacket implements PocketProtocolConstants{
	public int seqNumber;
	public ArrayList<ReceivedEncapsulatedPacket> packets = new ArrayList<ReceivedEncapsulatedPacket>(1);
	public RaknetReceivedCustomPacket(ByteBuffer bb){
		seqNumber = Utils.readLTriad(bb);
		while(bb.hasRemaining()){
			packets.add(new ReceivedEncapsulatedPacket(bb));
		}
	}
	public static class ReceivedEncapsulatedPacket{
		public byte reliability;
		public boolean hasSplit;
		public int messageIndex = -1;
		public int orderIndex = -1;
		public byte orderChannel = (byte) 0xFF;
		public int splitCount = -1;
		public short splitId = -1;
		public int splitIndex = -1;
		public byte[] buffer;
		public ReceivedEncapsulatedPacket(ByteBuffer bb){
			byte flag = bb.get();
			reliability = (byte) (flag >> 5);
			hasSplit = (flag & 0x10) == 0x10;
			int length = bb.getShort() * 8;
			//int length = ((bb.getShort() + 7) >> 3);
			if(Utils.inArray(reliability, RAKNET_HAS_MESSAGE_RELIABILITIES)){
				messageIndex = Utils.readLTriad(bb);
			}
			if(Utils.inArray(reliability, RAKNET_HAS_ORDER_RELIABILITIES)){
				orderIndex = Utils.readLTriad(bb);
				orderChannel = bb.get();
			}
			if(hasSplit){
				splitCount = bb.getInt();
				splitId = bb.getShort();
				splitIndex = bb.getInt();
			}
			buffer = new byte[length];
			bb.get(buffer);
		}
	}
}
