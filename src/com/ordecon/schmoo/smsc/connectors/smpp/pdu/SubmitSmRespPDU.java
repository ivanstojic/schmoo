package com.ordecon.schmoo.smsc.connectors.smpp.pdu;
import com.ordecon.schmoo.smsc.connectors.smpp.pdu.BasicPDU;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPInputStream;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPOutputStream;
import java.io.*;

public class SubmitSmRespPDU extends BasicPDU {
    public SubmitSmRespPDU () {
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
		default:
		    /* skip_stuff(); */
		    in.skip( len );
	    }
	}
    }

    public void write( SMPPOutputStream out ) throws IOException {
	out.writeHeader( 0x80000004, getStatus(), getSequence() );
	/* Dumping mandatory parameters */
	out.writeCString( m_mand_messageId, false);

	/* And now, the optional parameters! */

    /* Finish writing the PDU. */
	out.endMessage();
    }

    public boolean equals ( Object o ) {
	if ( o instanceof SubmitSmRespPDU ) {
	    SubmitSmRespPDU p = (SubmitSmRespPDU) o;

	    /* Testing mandatory parameters for equality */
	    if ( !( m_mand_messageId.equals(p.getMessageId())  ) ) { return false; }

	    /* Testing optional parameters for equality */

	} else {
	    return false;
	}
	return true;
    }

    public String toString() {
	return "PDU: submit_sm_resp (0x80000004) seq " + getSequence() + "\n"
	    + "\tMandatory parameters:\n"
	    + "\t\tmessageId = "
	    + "\"" + m_mand_messageId.toString() + "\""
	    + ", "

	    + "\tOptional parameters:\n"
	;
    }


    /* Parameter getters */
    public String getMessageId () {
	return m_mand_messageId;
    }


    /* Parameter setters */
    public void setMessageId ( String messageId ) {
	m_mand_messageId = messageId;
    }


    /* Constant values */
    int UNDEFINED_VALUE = 0;

    /* Define private variables - mandatory params*/
    private String  m_mand_messageId = "";

    /* Define private variables - optional params */
}
