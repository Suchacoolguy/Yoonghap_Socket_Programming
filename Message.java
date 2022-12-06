public class Message {
    public static final byte OWNER_SERVICE = 1;
    public static final byte CUSTOMER_SERVICE = 2;
    public static final byte ADMIN_SERVICE = 3;

    public static final byte REQUEST = 1;
    public static final byte RESPONSE = 2;
    public static final byte RESULT = 3;


    private byte serviceType = 0;
    private byte subType = 0;
    private byte code = 0;
    private byte idLength = 0;
    private byte pwLength = 0;
    private byte[] data = null;
    private String id = null;
    private String pw = null;


    public Message()
    {
        serviceType = 0;
        subType = 0;
        code = 0;
        idLength = 0;
        pwLength = 0;
        data = null;
        id = null;
        pw = null;
    }

    public byte getServiceType() {
        return serviceType;
    }

    public void setServiceType(byte serviceType) {
        this.serviceType = serviceType;
    }

    public byte getSubType() {
        return subType;
    }

    public void setSubType(byte subType) {
        this.subType = subType;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public byte getIdLength() {
        return idLength;
    }

    public void setIdLength(byte idLength) {
        this.idLength = idLength;
    }

    public byte getPwLength() {
        return pwLength;
    }

    public void setPwLength(byte pwLength) {
        this.pwLength = pwLength;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public short getBodyLength()
    {
        return (short) (idLength + pwLength);
    }
}
