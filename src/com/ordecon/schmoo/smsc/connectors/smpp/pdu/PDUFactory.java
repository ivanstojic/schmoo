package com.ordecon.schmoo.smsc.connectors.smpp.pdu;

import java.io.IOException;
import com.ordecon.schmoo.smsc.connectors.smpp.pdu.BasicPDU;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.*;

public class PDUFactory {

    public static int PDU_GENERIC_NACK = 0x80000000;
    public static int PDU_BIND_TRANSCEIVER = 0x00000009;
    public static int PDU_BIND_TRANSCEIVER_RESP = 0x80000009;
    public static int PDU_BIND_TRANSMITTER = 0x00000002;
    public static int PDU_BIND_TRANSMITTER_RESP = 0x80000002;
    public static int PDU_BIND_RECIEVER = 0x00000001;
    public static int PDU_BIND_RECIEVER_RESP = 0x80000001;
    public static int PDU_UNBIND = 0x00000006;
    public static int PDU_UNBIND_RESP = 0x80000006;
    public static int PDU_DELIVER_SM = 0x00000005;
    public static int PDU_DELIVER_SM_RESP = 0x80000005;
    public static int PDU_SUBMIT_SM = 0x00000004;
    public static int PDU_SUBMIT_SM_RESP = 0x80000004;
    public static int PDU_ENQUIRE_LINK = 0x00000015;
    public static int PDU_ENQUIRE_LINK_RESP = 0x80000015;

    public static BasicPDU getPDU(int id) {
        BasicPDU pdu;

        switch (id) {
            case 0x80000000:
                pdu = new GenericNackPDU();
                break;
            case 0x00000009:
                pdu = new BindTransceiverPDU();
                break;
            case 0x80000009:
                pdu = new BindTransceiverRespPDU();
                break;
            case 0x00000002:
                pdu = new BindTransmitterPDU();
                break;
            case 0x80000002:
                pdu = new BindTransmitterRespPDU();
                break;
            case 0x00000001:
                pdu = new BindRecieverPDU();
                break;
            case 0x80000001:
                pdu = new BindRecieverRespPDU();
                break;
            case 0x00000006:
                pdu = new UnbindPDU();
                break;
            case 0x80000006:
                pdu = new UnbindRespPDU();
                break;
            case 0x00000005:
                pdu = new DeliverSmPDU();
                break;
            case 0x80000005:
                pdu = new DeliverSmRespPDU();
                break;
            case 0x00000004:
                pdu = new SubmitSmPDU();
                break;
            case 0x80000004:
                pdu = new SubmitSmRespPDU();
                break;
            case 0x00000015:
                pdu = new EnquireLinkPDU();
                break;
            case 0x80000015:
                pdu = new EnquireLinkRespPDU();
                break;
            default:
                pdu = new BasicPDU();
        }

        pdu.setStatus(0);
        return pdu;
    }



    public static BasicPDU readPDU(SMPPInputStream in) throws IOException {
        int len, id, status, seq;

        len = in.readInteger();
        id = in.readInteger();
        status = in.readInteger();
        seq = in.readInteger();

        BasicPDU pdu = PDUFactory.getPDU(id);

        pdu.setId(id);
        pdu.setStatus(status);
        pdu.setSequence(seq);
        pdu.setLength(len);

        if (id == -1 || ((pdu instanceof BindTransmitterRespPDU || pdu instanceof BindRecieverRespPDU || pdu instanceof BindTransceiverRespPDU) && status != 0)) {
            return pdu;
        }

        pdu.read(in, len - 16);
        return pdu;
    }
}
