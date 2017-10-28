/*
 Copyright 2006-2011 Abdulla Abdurakhmanov (abdulla@latestbit.com)
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.bn.coders;

import java.io.ByteArrayOutputStream;
import org.bn.IEncoder;
import org.bn.coders.test_asn.*;
import org.bn.utils.ByteTools;
import static org.junit.Assert.*;
import org.junit.Test;

public abstract class EncoderTest {
    
    private final CoderTestUtilities coderTestUtils;
    
    public EncoderTest(CoderTestUtilities coderTestUtils) {
        this.coderTestUtils = coderTestUtils;
    }        
    
    @SuppressWarnings("unchecked")
    protected void printEncoded(String details, IEncoder encoder, Object obj) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        encoder.encode(obj, outputStream);
        System.out.println("Encoded by "+encoder.getClass()+" ("+details+") : " + ByteTools.byteArrayToHexString(outputStream.toByteArray()));        
    }
    
    protected abstract IEncoder newEncoder();
    
    /**
     * @see Encoder#encode(Object,OutputStream)
     */
    @Test
    public void testEncodeChoice() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        Data choice = new Data();        

        choice.selectBinary( new TestOCT( new byte[] { (byte)0xFF } ) );
        printEncoded("Choice boxed octet",encoder,choice);
        checkEncoded(encoder, choice, coderTestUtils.createDataChoiceTestOCTBytes());

        choice.selectSimpleType("aaaaaaa");
        printEncoded("Choice string", encoder,choice);
        checkEncoded(encoder, choice, coderTestUtils.createDataChoiceSimpleTypeBytes());
        
        choice.selectBooleanType(true);
        printEncoded("Choice boolean",encoder,choice);
        checkEncoded(encoder, choice, coderTestUtils.createDataChoiceBooleanBytes());
        
        choice.selectIntBndType(7);
        printEncoded("Choice boxed int",encoder,choice);
        checkEncoded(encoder, choice, coderTestUtils.createDataChoiceIntBndBytes());
        
        choice.selectPlain(new TestPRN("bbbbbb"));
        printEncoded("Choice plain",encoder,choice);
        checkEncoded(encoder, choice, coderTestUtils.createDataChoicePlainBytes());
        
        choice.selectSimpleOctType(new byte[10] );
        printEncoded("Choice simple octet",encoder,choice);
        choice.selectIntType(7L);
        printEncoded("Choice simple int",encoder,choice);
    }
        
    /**
     * @see Encoder#encode(Object,OutputStream)
     */
    @Test
    public void testEncode() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("SequenceMO test",encoder, coderTestUtils.createDataSeqMO());
        checkEncoded(encoder, coderTestUtils.createDataSeqMO(), coderTestUtils.createDataSeqMOBytes());
    
        printEncoded("Sequence test",encoder, coderTestUtils.createDataSeq());
        checkEncoded(encoder, coderTestUtils.createDataSeq(), coderTestUtils.createDataSeqBytes());
    }

    /**
     * @see Encoder#encode(Object,OutputStream)
     */
    @Test
    public void testITUEncode() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("ITUSequence test",encoder, coderTestUtils.createITUSeq());
        checkEncoded(encoder, coderTestUtils.createITUSeq(), coderTestUtils.createITUSeqBytes());
    }

    @SuppressWarnings("unchecked")
    protected void checkEncoded(IEncoder encoder, Object obj, byte[] standard) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        encoder.encode(obj, outputStream);
        assertArrayEquals(standard, outputStream.toByteArray());
    }
    
    @Test
    public void testNullEncode() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("NullSequence test",encoder, coderTestUtils.createNullSeq());
        checkEncoded(encoder, coderTestUtils.createNullSeq(), coderTestUtils.createNullSeqBytes());
    }
    
    @Test
    public void testTaggedNullEncode() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("TaggedNullSequence test",encoder, coderTestUtils.createTaggedNullSeq());
        checkEncoded(encoder, coderTestUtils.createTaggedNullSeq(), coderTestUtils.createTaggedNullSeqBytes());        
    }
   
    @Test
    public void testEnum() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Enum test",encoder, coderTestUtils.createEnum());
        checkEncoded(encoder, coderTestUtils.createEnum(), coderTestUtils.createEnumBytes());
    }    

    @Test
    public void testSequenceWithEnum() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Sequence Enum test",encoder, coderTestUtils.createSequenceWithEnum());
        checkEncoded(encoder, coderTestUtils.createSequenceWithEnum(), coderTestUtils.createSequenceWithEnumBytes());
    }
    
    @Test
    public void testSequenceOfString() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Sequence Of String",encoder, coderTestUtils.createStringArray());
        checkEncoded(encoder, coderTestUtils.createStringArray(), coderTestUtils.createStringArrayBytes());
    }
    
    @Test
    public void testRecursiveDefinition() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Recursive test",encoder, coderTestUtils.createTestRecursiveDefinition());
        checkEncoded(encoder, coderTestUtils.createTestRecursiveDefinition(), coderTestUtils.createTestRecursiveDefinitionBytes());
    }
    
    @Test
    public void testEncodeInteger() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Unbounded integer test",encoder, coderTestUtils.createUnboundedTestInteger());
        checkEncoded(encoder, coderTestUtils.createUnboundedTestInteger(), coderTestUtils.createUnboundedTestIntegerBytes());
        printEncoded("Integer2_12 test",encoder, coderTestUtils.createTestInteger2_12());
        checkEncoded(encoder, coderTestUtils.createTestInteger2_12(), coderTestUtils.createTestInteger2_12Bytes());
        
        printEncoded("IntegerR test",encoder, coderTestUtils.createTestIntegerR());
        checkEncoded(encoder, coderTestUtils.createTestIntegerR(), coderTestUtils.createTestIntegerRBytes());        
        
        printEncoded("Integer4 test",encoder, coderTestUtils.createTestInteger4());
        checkEncoded(encoder, coderTestUtils.createTestInteger4(), coderTestUtils.createTestInteger4Bytes());
        printEncoded("Integer3 test",encoder, coderTestUtils.createTestInteger3());
        checkEncoded(encoder, coderTestUtils.createTestInteger3(), coderTestUtils.createTestInteger3Bytes());
        printEncoded("Integer2 test",encoder, coderTestUtils.createTestInteger2());
        checkEncoded(encoder, coderTestUtils.createTestInteger2(), coderTestUtils.createTestInteger2Bytes());

        printEncoded("Integer1 test",encoder, coderTestUtils.createTestInteger1());
        checkEncoded(encoder, coderTestUtils.createTestInteger1(), coderTestUtils.createTestInteger1Bytes());        
    }

    @Test
    public void testEncodeString() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("TestPRN",encoder, coderTestUtils.createTestPRN());
        checkEncoded(encoder, coderTestUtils.createTestPRN(), coderTestUtils.createTestPRNBytes());

        printEncoded("TestIA5",encoder, coderTestUtils.createTestIA5());
        checkEncoded(encoder, coderTestUtils.createTestIA5(), coderTestUtils.createTestIA5Bytes());

        printEncoded("TestOCT",encoder, coderTestUtils.createTestOCT());
        checkEncoded(encoder, coderTestUtils.createTestOCT(), coderTestUtils.createTestOCTBytes());        
    }
    
    @Test
    public void testEncodeNegativeInteger() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Negative integer test",encoder, coderTestUtils.createTestNI());
        checkEncoded(encoder, coderTestUtils.createTestNI(), coderTestUtils.createTestNIBytes());
        printEncoded("Negative integer test 2",encoder, coderTestUtils.createTestNI2());
        checkEncoded(encoder, coderTestUtils.createTestNI2(), coderTestUtils.createTestNI2Bytes());        
    }
    
    @Test
    public void testEncodeSet() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Set test", encoder, coderTestUtils.createSet());            
        checkEncoded(encoder, coderTestUtils.createSet(), coderTestUtils.createSetBytes());
    }
    
    @Test
    public void testEncodeSetWithDefaultValue() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Set test with default value", encoder, coderTestUtils.createSetWithDefaultValue());            
        checkEncoded(encoder, coderTestUtils.createSetWithDefaultValue(), coderTestUtils.createSetWithDefaultValueBytes());
    }
    
    @Test
    public void testEncodeSequenceWithDefaultValues() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Sequence test with default values", encoder, coderTestUtils.createSequenceWithDefaultValues());
        checkEncoded(encoder, coderTestUtils.createSequenceWithDefaultValues(), coderTestUtils.createSequenceWithDefaultValuesBytes());
    }
    
    @Test
    public void testEncodeSequenceWithUntouchedDefaultValues() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Sequence test with untouched default values", encoder, coderTestUtils.createSequenceWithUntouchedDefaultValues());
        checkEncoded(encoder, coderTestUtils.createSequenceWithUntouchedDefaultValues(), coderTestUtils.createSequenceWithDefaultValuesBytes());
    }
    
    @Test
    public void testEncodeBitString() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("TestBitStr test",encoder, coderTestUtils.createTestBitStr());            
        checkEncoded(encoder, coderTestUtils.createTestBitStr(), coderTestUtils.createTestBitStrBytes());
    }

    @Test
    public void testEncodeBitStringSmall() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("TestBitStrSmall test",encoder, coderTestUtils.createTestBitStrSmall());            
        checkEncoded(encoder, coderTestUtils.createTestBitStrSmall(), coderTestUtils.createTestBitStrSmallBytes());
    }

    @Test
    public void testEncodeBitStringBnd() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("TestBitStrBnd test",encoder, coderTestUtils.createTestBitStrBnd());            
        checkEncoded(encoder, coderTestUtils.createTestBitStrBnd(), coderTestUtils.createTestBitStrBndBytes());
    }
    
    @Test
    public void testEncodeUnicodeString() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("TestUnicode test",encoder, coderTestUtils.createUnicodeStr());            
        checkEncoded(encoder, coderTestUtils.createUnicodeStr(), coderTestUtils.createUnicodeStrBytes());
    }
    
    @Test
    public void testEncodeVersion1_2() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("TestEncodeVersion_1_2: ",encoder, coderTestUtils.createTestSequenceV12());            
        checkEncoded(encoder, coderTestUtils.createTestSequenceV12(), coderTestUtils.createTestSequenceV12Bytes());        
    }
    
    @Test
    public void testEncodeChoiceInChoice() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("EncodeChoiceInChoice: ",encoder, coderTestUtils.createChoiceInChoice());            
        checkEncoded(encoder, coderTestUtils.createChoiceInChoice(), coderTestUtils.createChoiceInChoiceBytes());        
    }

    @Test
    public void testEncodeChoiceInChoice2() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("EncodeChoiceInChoice2: ",encoder, coderTestUtils.createChoiceInChoice2());            
        checkEncoded(encoder, coderTestUtils.createChoiceInChoice2(), coderTestUtils.createChoiceInChoice2Bytes());        
    }

    @Test
    public void testEncodeChoiceInChoice3() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("EncodeChoiceInChoice3: ",encoder, coderTestUtils.createChoiceInChoice3());            
        checkEncoded(encoder, coderTestUtils.createChoiceInChoice3(), coderTestUtils.createChoiceInChoice3Bytes());        
    }

    @Test
    public void testEncodeTaggedSeqInSeq() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("EncodeTaggedSeqInSeq: ",encoder, coderTestUtils.createTaggedSeqInSeq());            
        checkEncoded(encoder, coderTestUtils.createTaggedSeqInSeq(), coderTestUtils.createTaggedSeqInSeqBytes());        
    }

    @Test
    public void testEncodeReals() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("EncodeTestReal1.5: ",encoder, coderTestUtils.createTestReal1_5());            
        checkEncoded(encoder, coderTestUtils.createTestReal1_5(), coderTestUtils.createTestReal1_5Bytes());        
        printEncoded("EncodeTestReal0.5: ",encoder, coderTestUtils.createTestReal0_5());            
        checkEncoded(encoder, coderTestUtils.createTestReal0_5(), coderTestUtils.createTestReal0_5Bytes());                
        printEncoded("EncodeTestReal2: ",encoder, coderTestUtils.createTestReal2());            
        checkEncoded(encoder, coderTestUtils.createTestReal2(), coderTestUtils.createTestReal2Bytes());        
        printEncoded("EncodeTestRealBig: ",encoder, coderTestUtils.createTestRealBig());            
        checkEncoded(encoder, coderTestUtils.createTestRealBig(), coderTestUtils.createTestRealBigBytes());        
        printEncoded("EncodeTestRealPosInf: ",encoder, coderTestUtils.createTestRealPosInf());
        checkEncoded(encoder, coderTestUtils.createTestRealPosInf(), coderTestUtils.createTestRealPosInfBytes());
        printEncoded("EncodeTestRealNegInf: ",encoder, coderTestUtils.createTestRealNegInf());
        checkEncoded(encoder, coderTestUtils.createTestRealNegInf(), coderTestUtils.createTestRealNegInfBytes());
        printEncoded("EncodeTestReal-1.5: ",encoder, coderTestUtils.createTestRealNeg1_5());
        checkEncoded(encoder, coderTestUtils.createTestRealNeg1_5(), coderTestUtils.createTestRealNeg1_5Bytes());
    }
    
    @Test
    public void testSequenceWithNull() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("SequenceWithNull test",encoder, coderTestUtils.createSeqWithNull());
        checkEncoded(encoder, coderTestUtils.createSeqWithNull(), coderTestUtils.createSeqWithNullBytes());
    }
    
    @Test
    public void testEncodeLongTag() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("EncodeTestLongTag: ",encoder, coderTestUtils.createTestLongTag());
        checkEncoded(encoder, coderTestUtils.createTestLongTag(), coderTestUtils.createTestLongTagBytes());                
    }    
    
    @Test
    public void testEncodeLongTag2() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("EncodeTestLongTag2: ",encoder, coderTestUtils.createTestLongTag2());
        checkEncoded(encoder, coderTestUtils.createTestLongTag2(), coderTestUtils.createTestLongTag2Bytes());                
    }

    @Test
    public void testSequenceOfUTFString() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Sequence Of UTF8String",encoder, coderTestUtils.createUTF8StringArray());
        checkEncoded(encoder, coderTestUtils.createUTF8StringArray(), coderTestUtils.createUTF8StringArrayBytes());
    }
    
    @Test
    public void testEncodeOID() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        //
        TestOID testOID = coderTestUtils.createTestOID1();
        printEncoded("OID "+testOID.getValue().getValue(), encoder, testOID);
        checkEncoded(encoder, coderTestUtils.createTestOID1(), coderTestUtils.createTestOID1Bytes());
        //
        testOID = coderTestUtils.createTestOID2();
        printEncoded("OID " + testOID.getValue().getValue(), encoder, testOID);
        checkEncoded(encoder, coderTestUtils.createTestOID2(), coderTestUtils.createTestOID2Bytes());
        //
        testOID = coderTestUtils.createTestOID3();
        printEncoded("OID " + testOID.getValue().getValue(), encoder, testOID);
        checkEncoded(encoder, coderTestUtils.createTestOID3(), coderTestUtils.createTestOID3Bytes());
        //
        testOID = coderTestUtils.createTestOID4();
        printEncoded("OID " + testOID.getValue().getValue(), encoder, testOID);
        checkEncoded(encoder, coderTestUtils.createTestOID4(), coderTestUtils.createTestOID4Bytes());
    }
    
    @Test
    public void testEncodeTaggedSet() throws Exception {
    	IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        //
        Config taggedSet = coderTestUtils.createTaggedSet();
        printEncoded("TaggedSet",encoder, taggedSet);
        checkEncoded(encoder, coderTestUtils.createTaggedSet(), coderTestUtils.createTaggedSetBytes());
    }

    @Test
    public void testEncodeTaggedSetInSet() throws Exception {
    	IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        
        TestTaggedSetInSet taggedSet = coderTestUtils.createTaggedSetInSet();
        printEncoded("TaggedSetInSet",encoder, taggedSet);
        checkEncoded(encoder, coderTestUtils.createTaggedSetInSet(), coderTestUtils.createTaggedSetInSetBytes());
        
        Set7 set7 = coderTestUtils.createSet7();
        printEncoded("Set7",encoder, set7);
        checkEncoded(encoder, coderTestUtils.createSet7(), coderTestUtils.createSet7Bytes());
    }
}
