package com.ordecon.schmoo.smsc.connectors.smpp.pdu;
import com.ordecon.schmoo.smsc.connectors.smpp.pdu.BasicPDU;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPInputStream;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPOutputStream;
import java.io.*;

public class EnquireLinkPDU extends BasicPDU {
    public EnquireLinkPDU () {
    }

    public void read( SMPPInputStream in, int length ) throws IOException {
	int tag, len;
	
	/* Read mandatory parameters, one by one */

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
	out.writeHeader( 0x00000015, getStatus(), getSequence() );
	/* Dumping mandatory parameters */

	/* And now, the optional parameters! */

    /* Finish writing the PDU. */
	out.endMessage();
    }

    public boolean equals ( Object o ) {
	if ( o instanceof EnquireLinkPDU ) {
	    EnquireLinkPDU p = (EnquireLinkPDU) o;

	    /* Testing mandatory parameters for equality */

	    /* Testing optional parameters for equality */

	} else {
	    return false;
	}
	return true;
    }

    public String toString() {
	return "PDU: enquire_link (0x00000015) seq " + getSequence() + "\n"
	    + "\tMandatory parameters:\n"

	    + "\tOptional parameters:\n"
	;
    }


    /* Parameter getters */


    /* Parameter setters */


    /* Constant values */
    int UNDEFINED_VALUE = 0;

    /* Define private variables - mandatory params*/

    /* Define private variables - optional params */
}
