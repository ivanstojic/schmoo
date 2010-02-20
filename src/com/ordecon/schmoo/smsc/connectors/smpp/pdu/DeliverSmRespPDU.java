package com.ordecon.schmoo.smsc.connectors.smpp.pdu;
import com.ordecon.schmoo.smsc.connectors.smpp.pdu.BasicPDU;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPInputStream;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPOutputStream;
import java.io.*;

public class DeliverSmRespPDU extends BasicPDU {
    public DeliverSmRespPDU () {
    }

    public void read( SMPPInputStream in, int length ) throws IOException {
	int tag, len;
	
	/* Read mandatory parameters, one by one */
	m_mand_messageId = in.readCString(65, false);
    length -= m_mand_messageId.length()+1;

	/* Read optional parameters */
	while ( length>0 ) {
	    tag = in.readShort();
	    len = in.readShort();
	    length -= 4 + len;

	    switch (tag) {
		/* stuff to generate a switch statement */
		case 0x0425:
		    m_opt_deliveryFailureReason = in.readByte();
		    break;
		case 0x0423:
		    m_opt_networkErrorCode = in.readCString(false);
		    break;
		case 0x001D:
		    m_opt_additionalStatusInfoText = in.readCString(false);
		    break;
		case 0x0420:
		    m_opt_dpfResult = in.readByte();
		    break;
		default:
		    /* skip_stuff(); */
		    in.skip( len );
	    }
	}
    }

    public void write( SMPPOutputStream out ) throws IOException {
	out.writeHeader( 0x80000005, getStatus(), getSequence() );
	/* Dumping mandatory parameters */
	out.writeCString( m_mand_messageId, false);

	/* And now, the optional parameters! */
	if ( !(m_opt_deliveryFailureReason == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0425 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_deliveryFailureReason );
	}
	if ( !(m_opt_networkErrorCode.equals("")) ) {
	    out.writeShort( 0x0423 );
	    out.writeShort(  m_opt_networkErrorCode.length()+1  );
	    out.writeCString( m_opt_networkErrorCode, false);
	}
	if ( !(m_opt_additionalStatusInfoText.equals("")) ) {
	    out.writeShort( 0x001D );
	    out.writeShort(  m_opt_additionalStatusInfoText.length()+1  );
	    out.writeCString( m_opt_additionalStatusInfoText, false);
	}
	if ( !(m_opt_dpfResult == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0420 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_dpfResult );
	}

    /* Finish writing the PDU. */
	out.endMessage();
    }

    public boolean equals ( Object o ) {
	if ( o instanceof DeliverSmRespPDU ) {
	    DeliverSmRespPDU p = (DeliverSmRespPDU) o;

	    /* Testing mandatory parameters for equality */
	    if ( !( m_mand_messageId.equals(p.getMessageId())  ) ) { return false; }

	    /* Testing optional parameters for equality */
	    if ( !( m_opt_deliveryFailureReason == p.getDeliveryFailureReason()  ) ) { return false; }
	    if ( !( m_opt_networkErrorCode.equals(p.getNetworkErrorCode())  ) ) { return false; }
	    if ( !( m_opt_additionalStatusInfoText.equals(p.getAdditionalStatusInfoText())  ) ) { return false; }
	    if ( !( m_opt_dpfResult == p.getDpfResult()  ) ) { return false; }

	} else {
	    return false;
	}
	return true;
    }

    public String toString() {
	return "PDU: deliver_sm_resp (0x80000005) seq " + getSequence() + "\n"
	    + "\tMandatory parameters:\n"
	    + "\t\tmessageId = "
	    + "\"" + m_mand_messageId.toString() + "\""
	    + ", "

	    + "\tOptional parameters:\n"
	    + ( !( m_opt_deliveryFailureReason == UNDEFINED_VALUE ) ?
		  "\t\tdeliveryFailureReason = "
		+ Integer.toString( m_opt_deliveryFailureReason ) + "\n"
	    : "" )
	    + ( !m_opt_networkErrorCode.equals("") ?
		  "\t\tnetworkErrorCode = "
		+ "\"" + m_opt_networkErrorCode.toString() + "\"\n"
	    : "" )
	    + ( !m_opt_additionalStatusInfoText.equals("") ?
		  "\t\tadditionalStatusInfoText = "
		+ "\"" + m_opt_additionalStatusInfoText.toString() + "\"\n"
	    : "" )
	    + ( !( m_opt_dpfResult == UNDEFINED_VALUE ) ?
		  "\t\tdpfResult = "
		+ Integer.toString( m_opt_dpfResult ) + "\n"
	    : "" )
	;
    }


    /* Parameter getters */
    public String getMessageId () {
	return m_mand_messageId;
    }
    public int getDeliveryFailureReason () {
	return m_opt_deliveryFailureReason;
    }
    public String getNetworkErrorCode () {
	return m_opt_networkErrorCode;
    }
    public String getAdditionalStatusInfoText () {
	return m_opt_additionalStatusInfoText;
    }
    public int getDpfResult () {
	return m_opt_dpfResult;
    }


    /* Parameter setters */
    public void setMessageId ( String messageId ) {
	m_mand_messageId = messageId;
    }
    public void setDeliveryFailureReason ( int deliveryFailureReason) {
	m_opt_deliveryFailureReason = deliveryFailureReason;
    }
    public void setNetworkErrorCode ( String networkErrorCode) {
	m_opt_networkErrorCode = networkErrorCode;
    }
    public void setAdditionalStatusInfoText ( String additionalStatusInfoText) {
	m_opt_additionalStatusInfoText = additionalStatusInfoText;
    }
    public void setDpfResult ( int dpfResult) {
	m_opt_dpfResult = dpfResult;
    }


    /* Constant values */
    int UNDEFINED_VALUE = 0;

    /* Define private variables - mandatory params*/
    private String  m_mand_messageId = "";

    /* Define private variables - optional params */
    private int  m_opt_deliveryFailureReason = UNDEFINED_VALUE;
    private String  m_opt_networkErrorCode = "";
    private String  m_opt_additionalStatusInfoText = "";
    private int  m_opt_dpfResult = UNDEFINED_VALUE;
}
