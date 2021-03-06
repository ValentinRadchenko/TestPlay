//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.13 at 04:35:08 PM EET 
//


package cert.cert.xsd;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for transferenceDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transferenceDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="olspRecId">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *               &lt;totalDigits value="10"/>
 *               &lt;minInclusive value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ebkTransactId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="10"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="surname">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="name">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="patronymic" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="birthday" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="ipn">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[0-9]{8,10}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ipnRefuse" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="phone" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="\+380[0-9]{9}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="doc" type="{}docType"/>
 *         &lt;element name="addr" type="{}addrType" minOccurs="0"/>
 *         &lt;element name="transactionDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="amount">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="12"/>
 *               &lt;fractionDigits value="2"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="bankCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;totalDigits value="6"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="settlementAccount" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *               &lt;totalDigits value="14"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="iban" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transferenceDataType", propOrder = {
    "olspRecId",
    "ebkTransactId",
    "surname",
    "name",
    "patronymic",
    "birthday",
    "ipn",
    "ipnRefuse",
    "phone",
    "doc",
    "addr",
    "transactionDate",
    "amount",
    "bankCode",
    "settlementAccount",
    "iban"
})
public class TransferenceDataType {

    protected long olspRecId;
    protected String ebkTransactId;
    @XmlElement(required = true)
    protected String surname;
    @XmlElement(required = true)
    protected String name;
    protected String patronymic;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar birthday;
    @XmlElement(required = true)
    protected String ipn;
    protected boolean ipnRefuse;
    protected String phone;
    @XmlElement(required = true)
    protected DocType doc;
    protected AddrType addr;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar transactionDate;
    @XmlElement(required = true)
    protected BigDecimal amount;
    protected Integer bankCode;
    protected Long settlementAccount;
    protected String iban;

    /**
     * Gets the value of the olspRecId property.
     * 
     */
    public long getOlspRecId() {
        return olspRecId;
    }

    /**
     * Sets the value of the olspRecId property.
     * 
     */
    public void setOlspRecId(long value) {
        this.olspRecId = value;
    }

    /**
     * Gets the value of the ebkTransactId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEbkTransactId() {
        return ebkTransactId;
    }

    /**
     * Sets the value of the ebkTransactId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEbkTransactId(String value) {
        this.ebkTransactId = value;
    }

    /**
     * Gets the value of the surname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the value of the surname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurname(String value) {
        this.surname = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the patronymic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Sets the value of the patronymic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatronymic(String value) {
        this.patronymic = value;
    }

    /**
     * Gets the value of the birthday property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthday() {
        return birthday;
    }

    /**
     * Sets the value of the birthday property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthday(XMLGregorianCalendar value) {
        this.birthday = value;
    }

    /**
     * Gets the value of the ipn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpn() {
        return ipn;
    }

    /**
     * Sets the value of the ipn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpn(String value) {
        this.ipn = value;
    }

    /**
     * Gets the value of the ipnRefuse property.
     * 
     */
    public boolean isIpnRefuse() {
        return ipnRefuse;
    }

    /**
     * Sets the value of the ipnRefuse property.
     * 
     */
    public void setIpnRefuse(boolean value) {
        this.ipnRefuse = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the doc property.
     * 
     * @return
     *     possible object is
     *     {@link DocType }
     *     
     */
    public DocType getDoc() {
        return doc;
    }

    /**
     * Sets the value of the doc property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocType }
     *     
     */
    public void setDoc(DocType value) {
        this.doc = value;
    }

    /**
     * Gets the value of the addr property.
     * 
     * @return
     *     possible object is
     *     {@link AddrType }
     *     
     */
    public AddrType getAddr() {
        return addr;
    }

    /**
     * Sets the value of the addr property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddrType }
     *     
     */
    public void setAddr(AddrType value) {
        this.addr = value;
    }

    /**
     * Gets the value of the transactionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the value of the transactionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTransactionDate(XMLGregorianCalendar value) {
        this.transactionDate = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Gets the value of the bankCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBankCode() {
        return bankCode;
    }

    /**
     * Sets the value of the bankCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBankCode(Integer value) {
        this.bankCode = value;
    }

    /**
     * Gets the value of the settlementAccount property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSettlementAccount() {
        return settlementAccount;
    }

    /**
     * Sets the value of the settlementAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSettlementAccount(Long value) {
        this.settlementAccount = value;
    }

    /**
     * Gets the value of the iban property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIban() {
        return iban;
    }

    /**
     * Sets the value of the iban property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIban(String value) {
        this.iban = value;
    }

}
