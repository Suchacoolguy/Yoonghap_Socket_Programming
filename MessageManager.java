public class MessageManager {

    public static Message OwnerSignUpIdRequest()
    {
        OwnerMessage message = new OwnerMessage();
        message.setServiceType(OwnerMessage.SIGN_UP);
        message.setSubType(OwnerMessage.REQUEST);
        message.setCode(OwnerMessage.ID);

        return message;
    }

    public static Message OwnerSignUpIdResponse()
    {
        OwnerMessage message = new OwnerMessage();
        message.setServiceType(OwnerMessage.SIGN_UP);
        message.setSubType(OwnerMessage.RESPONSE);
        message.setCode(OwnerMessage.ID);

        return message;
    }

    public static final Message OwnerSignUpPwRequest()
    {
        OwnerMessage message = new OwnerMessage();
        message.setServiceType(OwnerMessage.SIGN_UP);
        message.setSubType(OwnerMessage.REQUEST);
        message.setCode(OwnerMessage.PW);

        return message;
    }

    public static final Message OwnerSignUpPwResponse()
    {
        OwnerMessage message = new OwnerMessage();
        message.setServiceType(OwnerMessage.SIGN_UP);
        message.setSubType(OwnerMessage.RESPONSE);
        message.setCode(OwnerMessage.PW);

        return message;
    }
}
