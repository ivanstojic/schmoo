package com.ordecon.schmoo.smsc.connectors.smpp.pdu;
import com.ordecon.schmoo.smsc.connectors.smpp.pdu.BasicPDU;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPInputStream;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPOutputStream;
import java.io.*;

public class BindRecieverRespPDU extends BasicPDU {
    public BindRecieverRespPDU () {
    }

    public void read( SMPPInputStream in, int length ) throws IOException {
	int tag, len;
	
	/* Read mandatory parameters, one by one */
	m_mand_systemId = in.readCString(false);
    length -= m_mand_systemId.length()+1;

	/* Read optional parameters */
	while ( length>0 ) {
	    tag = in.readShort();
	    len = in.readShort();
	    length -= 4 + len;

	    switch (tag) {
		/* stuff to generate a switch statement */
		case 0x0210:
		    m_opt_scInterfaceVersion = in.readByte();
		    break;
		default:
		    /* skip_stuff(); */
		    in.skip( len );
	    }
	}
    }

    public void write( SMPPOutputStream out ) throws IOException {
	out.writeHeader( 0x80000001, getStatus(), getSequence() );
	/* Dumping mandatory parameters */
	out.writeCString( m_mand_systemId, false);

	/* And now, the optional parameters! */
	if ( !(m_opt_scInterfaceVersion == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0210 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_scInterfaceVersion );
	}

    /* Finish writing the PDU. */
	out.endMessage();
    }

    public boolean equals ( Object o ) {
	if ( o instanceof BindRecieverRespPDU ) {
	    BindRecieverRespPDU p = (BindRecieverRespPDU) o;

	    /* Testing mandatory parameters for equality */
	    if ( !( m_mand_systemId.equals(p.getSystemId())  ) ) { return false; }

	    /* Testing optional parameters for equality */
	    if ( !( m_opt_scInterfaceVersion == p.getScInterfaceVersion()  ) ) { return false; }

	} else {
	    return false;
	}
	return true;
    }

    public String toString() {
	return "PDU: bind_reciever_resp (0x80000001) seq " + getSequence() + "\n"
	    + "\tMandatory parameters:\n"
	    + "\t\tsystemId = "
	    + "\"" + m_mand_systemId.toString() + "\""
	    + ", "

	    + "\tOptional parameters:\n"
	    + ( !( m_opt_scInterfaceVersion == UNDEFINED_VALUE ) ?
		  "\t\tscInterfaceVersion = "
		+ Integer.toString( m_opt_scInterfaceVersion ) + "\n"
	    : "" )
	;
    }


    /* Parameter getters */
    public String getSystemId () {
	return m_mand_systemId;
    }
    public int getScInterfaceVersion () {
	return m_opt_scInterfaceVersion;
    }


    /* Parameter setters */
    public void setSystemId ( String systemId ) {
	m_mand_systemId = systemId;
    }
    public void setScInterfaceVersion ( int scInterfaceVersion) {
	m_opt_scInterfaceVersion = scInterfaceVersion;
    }


    /* Constant values */
    int UNDEFINED_VALUE = 0;

    /* Define private variables - mandatory params*/
    private String  m_mand_systemId = "";

    /* Define private variables - optional params */
    private int  m_opt_scInterfaceVersion = UNDEFINED_VALUE;
}
