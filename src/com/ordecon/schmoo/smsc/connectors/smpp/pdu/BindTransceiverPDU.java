package com.ordecon.schmoo.smsc.connectors.smpp.pdu;
import com.ordecon.schmoo.smsc.connectors.smpp.pdu.BasicPDU;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPInputStream;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPOutputStream;
import java.io.*;

public class BindTransceiverPDU extends BasicPDU {
    public BindTransceiverPDU () {
    }

    public void read( SMPPInputStream in, int length ) throws IOException {
	int tag, len;
	
	/* Read mandatory parameters, one by one */
	m_mand_systemId = in.readCString(16, false);
    length -= m_mand_systemId.length()+1;
	m_mand_password = in.readCString(9, false);
    length -= m_mand_password.length()+1;
	m_mand_systemType = in.readCString(13, false);
    length -= m_mand_systemType.length()+1;
	m_mand_interfaceVersion = in.readByte();
	length -= 1;
	m_mand_addrTon = in.readByte();
	length -= 1;
	m_mand_addrNpi = in.readByte();
	length -= 1;
	m_mand_addressRange = in.readCString(41, false);
    length -= m_mand_addressRange.length()+1;

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
	out.writeHeader( 0x00000009, getStatus(), getSequence() );
	/* Dumping mandatory parameters */
	out.writeCString( m_mand_systemId, false);
	out.writeCString( m_mand_password, false);
	out.writeCString( m_mand_systemType, false);
	out.writeByte( m_mand_interfaceVersion );
	out.writeByte( m_mand_addrTon );
	out.writeByte( m_mand_addrNpi );
	out.writeCString( m_mand_addressRange, false);

	/* And now, the optional parameters! */

    /* Finish writing the PDU. */
	out.endMessage();
    }

    public boolean equals ( Object o ) {
	if ( o instanceof BindTransceiverPDU ) {
	    BindTransceiverPDU p = (BindTransceiverPDU) o;

	    /* Testing mandatory parameters for equality */
	    if ( !( m_mand_systemId.equals(p.getSystemId())  ) ) { return false; }
	    if ( !( m_mand_password.equals(p.getPassword())  ) ) { return false; }
	    if ( !( m_mand_systemType.equals(p.getSystemType())  ) ) { return false; }
	    if ( !( m_mand_interfaceVersion == p.getInterfaceVersion()  ) ) { return false; }
	    if ( !( m_mand_addrTon == p.getAddrTon()  ) ) { return false; }
	    if ( !( m_mand_addrNpi == p.getAddrNpi()  ) ) { return false; }
	    if ( !( m_mand_addressRange.equals(p.getAddressRange())  ) ) { return false; }

	    /* Testing optional parameters for equality */

	} else {
	    return false;
	}
	return true;
    }

    public String toString() {
	return "PDU: bind_transceiver (0x00000009) seq " + getSequence() + "\n"
	    + "\tMandatory parameters:\n"
	    + "\t\tsystemId = "
	    + "\"" + m_mand_systemId.toString() + "\""
	    + ", "
	    + "\t\tpassword = "
	    + "\"" + m_mand_password.toString() + "\""
	    + ", "
	    + "\t\tsystemType = "
	    + "\"" + m_mand_systemType.toString() + "\""
	    + ", "
	    + "\t\tinterfaceVersion = "
	    + Integer.toString( m_mand_interfaceVersion )
	    + ", "
	    + "\t\taddrTon = "
	    + Integer.toString( m_mand_addrTon )
	    + ", "
	    + "\t\taddrNpi = "
	    + Integer.toString( m_mand_addrNpi )
	    + ", "
	    + "\t\taddressRange = "
	    + "\"" + m_mand_addressRange.toString() + "\""
	    + ", "

	    + "\tOptional parameters:\n"
	;
    }


    /* Parameter getters */
    public String getSystemId () {
	return m_mand_systemId;
    }
    public String getPassword () {
	return m_mand_password;
    }
    public String getSystemType () {
	return m_mand_systemType;
    }
    public int getInterfaceVersion () {
	return m_mand_interfaceVersion;
    }
    public int getAddrTon () {
	return m_mand_addrTon;
    }
    public int getAddrNpi () {
	return m_mand_addrNpi;
    }
    public String getAddressRange () {
	return m_mand_addressRange;
    }


    /* Parameter setters */
    public void setSystemId ( String systemId ) {
	m_mand_systemId = systemId;
    }
    public void setPassword ( String password ) {
	m_mand_password = password;
    }
    public void setSystemType ( String systemType ) {
	m_mand_systemType = systemType;
    }
    public void setInterfaceVersion ( int interfaceVersion ) {
	m_mand_interfaceVersion = interfaceVersion;
    }
    public void setAddrTon ( int addrTon ) {
	m_mand_addrTon = addrTon;
    }
    public void setAddrNpi ( int addrNpi ) {
	m_mand_addrNpi = addrNpi;
    }
    public void setAddressRange ( String addressRange ) {
	m_mand_addressRange = addressRange;
    }


    /* Constant values */
    int UNDEFINED_VALUE = 0;

    /* Define private variables - mandatory params*/
    private String  m_mand_systemId = "";
    private String  m_mand_password = "";
    private String  m_mand_systemType = "";
    private int  m_mand_interfaceVersion = UNDEFINED_VALUE;
    private int  m_mand_addrTon = UNDEFINED_VALUE;
    private int  m_mand_addrNpi = UNDEFINED_VALUE;
    private String  m_mand_addressRange = "";

    /* Define private variables - optional params */
}
