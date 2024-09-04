package com.czertainly.api.interfaces.core.cmp.error;

import com.czertainly.api.interfaces.core.cmp.PkiMessageError;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.cmp.PKIBody;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception for error handling for cmp failure scenarios.
 * <p>
 * citation (from RFC 4210):
 *      This message MAY be generated at any time during a PKI transaction.
 *
 * @see <a href="https://www.rfc-editor.org/rfc/rfc4210#section-5.3.21">...</a>
 */
public class CmpBaseException extends Exception {

    private static final Logger LOG = LoggerFactory.getLogger(CmpBaseException.class.getName());

    protected int failureInfo;
    protected String errorDetails;

    protected CmpBaseException(Exception ex) {
        super(ex);
        if (ex instanceof CmpBaseException) {
            this.failureInfo = ((CmpBaseException) ex).failureInfo;
            this.errorDetails = ((CmpBaseException) ex).errorDetails;
            LOG.error("exception at: {}", errorDetails, ex);
        } else {
            this.failureInfo = PKIFailureInfo.systemFailure;
            this.errorDetails = ex.getLocalizedMessage();
            LOG.error("exception at: ", ex);
        }
    }

    /**
     * @param failureInfo   cmp failureInfo for CMP error message
     * @param errorDetails  description of details related to the error
     * @param ex            the underlying exception
     */
    protected CmpBaseException(ASN1OctetString tid, int failureInfo, String errorDetails, Throwable ex) {
        super(ex == null || ex.getMessage() == null ? "(TID="+(tid == null ? "n/a" : tid.toString())+") "+errorDetails : "(TID="+(tid == null ? "n/a" : tid.toString())+") "+ex.getMessage(),
                ex == null || ex.getCause() == null ? ex : ex.getCause());
        if (ex instanceof CmpBaseException) {
            this.failureInfo = ((CmpBaseException) ex).failureInfo;
            this.errorDetails = ((CmpBaseException) ex).errorDetails;
            if(tid != null) LOG.error("exception at: {} {}", tid, errorDetails, ex);
            else LOG.error("exception at: {}", errorDetails, ex);
        } else {
            this.failureInfo = failureInfo;
            this.errorDetails = errorDetails;
            if (ex != null) {
                if(tid==null)LOG.error("exception at: ", ex);
                else LOG.error("exception at: {} ", tid, ex);
            } else {
                if(tid==null) LOG.error("error at: {}", errorDetails);
                else LOG.error("error at: {} {}", tid, errorDetails);
            }
        }
    }

    /**
     * @return help to build {@link PKIBody} for response flow
     */
    public PKIBody toPKIBody() { return PkiMessageError.generateBody(failureInfo, errorDetails); }

    @Override
    public String toString() { return this.getClass().getSimpleName()+" [failureInfo=" + failureInfo + ", errorDetails=" + errorDetails + "]"; }
}
