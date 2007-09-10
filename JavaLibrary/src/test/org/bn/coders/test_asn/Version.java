
package test.org.bn.coders.test_asn;
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source ASN.1. 
//

import org.bn.*;
import org.bn.annotations.*;
import org.bn.annotations.constraints.*;
import org.bn.coders.*;
import org.bn.types.*;




    @ASN1PreparedElement
    @ASN1Sequence ( name = "Version", isSet = true )
    public class Version implements IASN1PreparedElement {
            
        @ASN1Element ( name = "minor", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private Minor minor = null;
                
  
        @ASN1Element ( name = "major", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private Major major = null;
                
  
        
        public Minor getMinor () {
            return this.minor;
        }

        

        public void setMinor (Minor value) {
            this.minor = value;
        }
        
  
        
        public Major getMajor () {
            return this.major;
        }

        

        public void setMajor (Major value) {
            this.major = value;
        }
        
  
                    
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(Version.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            