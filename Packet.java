public class Packet{
   // Size (in bytes) of each field in packet format
   public static final int LEN_HEADER = 9;
   public static final int LEN_TYPECODE = 1; // Type: upper 4 bits, Code: lower 4 bits
   public static final int LEN_SEQ = 4;
   public static final int LEN_ID = 1;
   public static final int LEN_PWD = 1;
   public static final int LEN_BODY = 2;

   // Protocol Type
   public static final byte REQUEST = 1;
   public static final byte RESPONSE = 2;
   public static final byte RESULT = 3;

   // Protocol Code
   public static final byte NOT_USED = 0;
   public static final byte SUCCESS = 1;
   public static final byte FAIL_ID = 2;    // Wrong ID
   public static final byte FAIL_PWD = 3;   // Wrong PWD

   public static byte[] intToBytes(int data) { 
      return new byte[] { 
         (byte)((data >> 24) & 0xff), 
         (byte)((data >> 16) & 0xff), 
         (byte)((data >> 8) & 0xff), 
         (byte)((data >> 0) & 0xff), 
      }; 
   }

   public static int bytesToInt(byte[] data) { 
      return (int)(
         (0xff & data[0]) << 24 | 
         (0xff & data[1]) << 16 | 
         (0xff & data[2]) << 8  | 
         (0xff & data[3]) << 0 
      ); 
   }

   public static byte[] shortToBytes(int data) { 
      return new byte[] { 
         (byte)((data >> 8) & 0xff), 
         (byte)((data >> 0) & 0xff), 
      }; 
   }

   public static short bytesToShort(byte[] data) { 
      return (short)(
         (0xff & data[0]) << 8 | 
         (0xff & data[1]) << 0 
      ); 
   }

   public static byte bitsToByte(byte data1, byte data2) { 
      return (byte)(
         (0x0f & data1) << 4 |  // Type: upper 4 bits
         (0x0f & data2) << 0    // Code: lower 4 bits
      ); 
   }

   public static byte[] byteToBits(byte data) { 
      return new byte[] { 
         (byte)((data >> 4) & 0x0f), 
         (byte)((data >> 0) & 0x0f), 
      };
   }

   public static byte[] makePacket(Message msg) {
      byte type = msg.getSubType();
      byte code = msg.getCode();
//      int seqNo = msg.getSeqNo();
      byte idLenByte = msg.getIdLength();
      byte pwdLenByte = msg.getPwLength();
      short bodyLen = msg.getBodyLength();
      String id = msg.getId();
      String pwd = msg.getPw();

      byte[] packet = new byte[LEN_HEADER + (int)bodyLen];
      int index = 0;   // index of packet byte array

      packet[index] = type;
      index = index + 1;

      packet[index] = code;
      index = index + 1;

//      byte[] seqNoByte = intToBytes(seqNo);
//      System.arraycopy(seqNoByte, 0, packet, index, seqNoByte.length);
//      index = index + seqNoByte.length;

      packet[index] = idLenByte;
      index = index + 1;

      packet[index] = pwdLenByte;
      index = index + 1;

      byte[] bodyLenByte = shortToBytes(bodyLen);
      System.arraycopy(bodyLenByte, 0, packet, index, bodyLenByte.length);
      index = index + bodyLenByte.length;

      if(bodyLen > 0){   // with ID and PWD
         byte[] idByte = id.getBytes();
         byte[] pwdByte = pwd.getBytes();

         System.arraycopy(idByte, 0, packet, index, idByte.length);
         index = index + idByte.length;
         System.arraycopy(pwdByte, 0, packet, index, pwdByte.length);
      }

      //System.out.println("Packet(Hex): " + convertBytesToHex(packet));
      return packet;
   }

   // Convert Bytes to Hex to see Packet
   public static String convertBytesToHex(byte[] bytes) {
      StringBuilder sb = new StringBuilder();
      for (byte temp : bytes) {
         sb.append(String.format("%02x", temp));
      }
      return sb.toString();
   }
}