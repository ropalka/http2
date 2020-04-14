/*
 * Copyright (c) 2012-2020, FOSS Nova Software foundation (FNSF),
 * and individual contributors as indicated by the @author tags.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.fossnova.http2;

import java.util.HashMap;
import java.util.Map;

import static org.fossnova.http2.Utils.validateScheme;

/**
 * HTTP status codes as defined by <a href="https://www.iana.org/assignments/http-status-codes/http-status-codes.xhtml">IANA</a> organization.
 *
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class StatusCode {

    /**
     * Status code <code>100</code>, see <code>RFC7231</code> section <code>6.2.1</code>.
     */
    public static final StatusCode CONTINUE = new StatusCode(100);
    /**
     * Status code <code>101</code>, see <code>RFC7231</code> section <code>6.2.2</code>.
     */
    public static final StatusCode SWITCHING_PROTOCOLS = new StatusCode(101);
    /**
     * Status code <code>102</code>, see <code>RFC2518</code>.
     */
    public static final StatusCode PROCESSING = new StatusCode(102);
    /**
     * Status code <code>103</code>, see <code>RFC8297</code>.
     */
    public static final StatusCode EARLY_HINTS = new StatusCode(103);
    /**
     * Status code <code>200</code>, see <code>RFC7231</code> section <code>6.3.1</code>.
     */
    public static final StatusCode OK = new StatusCode(200);
    /**
     * Status code <code>201</code>, see <code>RFC7231</code> section <code>6.3.2</code>.
     */
    public static final StatusCode CREATED = new StatusCode(201);
    /**
     * Status code <code>202</code>, see <code>RFC7231</code> section <code>6.3.3</code>.
     */
    public static final StatusCode ACCEPTED = new StatusCode(202);
    /**
     * Status code <code>203</code>, see <code>RFC7231</code> section <code>6.3.4</code>.
     */
    public static final StatusCode NON_AUTHORITATIVE_INFORMATION = new StatusCode(203);
    /**
     * Status code <code>204</code>, see <code>RFC7231</code> section <code>6.3.5</code>.
     */
    public static final StatusCode NO_CONTENT = new StatusCode(204);
    /**
     * Status code <code>205</code>, see <code>RFC7231</code> section <code>6.3.6</code>.
     */
    public static final StatusCode RESET_CONTENT = new StatusCode(205);
    /**
     * Status code <code>206</code>, see <code>RFC7233</code> section <code>4.1</code>.
     */
    public static final StatusCode PARTIAL_CONTENT = new StatusCode(206);
    /**
     * Status code <code>207</code>, see <code>RFC4918</code>.
     */
    public static final StatusCode MULTI_STATUS = new StatusCode(207);
    /**
     * Status code <code>208</code>, see <code>RFC5842</code>.
     */
    public static final StatusCode ALREADY_REPORTED = new StatusCode(208);
    /**
     * Status code <code>226</code>, see <code>RFC3229</code>.
     */
    public static final StatusCode IM_USED = new StatusCode(226);
    /**
     * Status code <code>300</code>, see <code>RFC7231</code> section <code>6.4.1</code>.
     */
    public static final StatusCode MULTIPLE_CHOICES = new StatusCode(300);
    /**
     * Status code <code>301</code>, see <code>RFC7231</code> section <code>6.4.2</code>.
     */
    public static final StatusCode MOVED_PERMANENTLY = new StatusCode(301);
    /**
     * Status code <code>302</code>, see <code>RFC7231</code> section <code>6.4.3</code>.
     */
    public static final StatusCode FOUND = new StatusCode(302);
    /**
     * Status code <code>303</code>, see <code>RFC7231</code> section <code>6.4.4</code>.
     */
    public static final StatusCode SEE_OTHER = new StatusCode(303);
    /**
     * Status code <code>304</code>, see <code>RFC7232</code> section <code>4.1</code>.
     */
    public static final StatusCode NOT_MODIFIED = new StatusCode(304);
    /**
     * Status code <code>305</code>, see <code>RFC7231</code> section <code>6.4.5</code>.
     */
    public static final StatusCode USE_PROXY = new StatusCode(305);
    /**
     * Status code <code>307</code>, see <code>RFC7231</code> section <code>6.4.7</code>.
     */
    public static final StatusCode TEMPORARY_REDIRECT = new StatusCode(307);
    /**
     * Status code <code>308</code>, see <code>RFC7538</code>.
     */
    public static final StatusCode PERMANENT_REDIRECT = new StatusCode(308);
    /**
     * Status code <code>400</code>, see <code>RFC7231</code> section <code>6.5.1</code>.
     */
    public static final StatusCode BAD_REQUEST = new StatusCode(400);
    /**
     * Status code <code>401</code>, see <code>RFC7235</code> section <code>3.1</code>.
     */
    public static final StatusCode UNAUTHORIZED = new StatusCode(401);
    /**
     * Status code <code>402</code>, see <code>RFC7231</code> section <code>6.5.2</code>.
     */
    public static final StatusCode PAYMENT_REQUIRED = new StatusCode(402);
    /**
     * Status code <code>403</code>, see <code>RFC7231</code> section <code>6.5.3</code>.
     */
    public static final StatusCode FORBIDDEN = new StatusCode(403);
    /**
     * Status code <code>404</code>, see <code>RFC7231</code> section <code>6.5.4</code>.
     */
    public static final StatusCode NOT_FOUND = new StatusCode(404);
    /**
     * Status code <code>405</code>, see <code>RFC7231</code> section <code>6.5.5</code>.
     */
    public static final StatusCode METHOD_NOT_ALLOWED = new StatusCode(405);
    /**
     * Status code <code>406</code>, see <code>RFC7231</code> section <code>6.5.6</code>.
     */
    public static final StatusCode NOT_ACCEPTABLE = new StatusCode(406);
    /**
     * Status code <code>407</code>, see <code>RFC7235</code> section <code>3.2</code>.
     */
    public static final StatusCode PROXY_AUTHENTICATION_REQUIRED = new StatusCode(407);
    /**
     * Status code <code>408</code>, see <code>RFC7231</code> section <code>6.5.7</code>.
     */
    public static final StatusCode REQUEST_TIMEOUT = new StatusCode(408);
    /**
     * Status code <code>409</code>, see <code>RFC7231</code> section <code>6.5.8</code>.
     */
    public static final StatusCode CONFLICT = new StatusCode(409);
    /**
     * Status code <code>410</code>, see <code>RFC7231</code> section <code>6.5.9</code>.
     */
    public static final StatusCode GONE = new StatusCode(410);
    /**
     * Status code <code>411</code>, see <code>RFC7231</code> section <code>6.5.10</code>.
     */
    public static final StatusCode LENGTH_REQUIRED = new StatusCode(411);
    /**
     * Status code <code>412</code>, see <code>RFC7232</code> section <code>4.2</code>.
     */
    public static final StatusCode PRECONDITION_FAILED = new StatusCode(412);
    /**
     * Status code <code>413</code>, see <code>RFC7231</code> section <code>6.5.11</code>.
     */
    public static final StatusCode PAYLOAD_TOO_LARGE = new StatusCode(413);
    /**
     * Status code <code>414</code>, see <code>RFC7231</code> section <code>6.5.12</code>.
     */
    public static final StatusCode URI_TOO_LONG = new StatusCode(414);
    /**
     * Status code <code>415</code>, see <code>RFC7231</code> section <code>6.5.13</code>.
     */
    public static final StatusCode UNSUPPORTED_MEDIA_TYPE = new StatusCode(415);
    /**
     * Status code <code>416</code>, see <code>RFC7233</code> section <code>4.4</code>.
     */
    public static final StatusCode RANGE_NOT_SATISFIABLE = new StatusCode(416);
    /**
     * Status code <code>417</code>, see <code>RFC7231</code> section <code>6.5.14</code>.
     */
    public static final StatusCode EXPECTATION_FAILED = new StatusCode(417);
    /**
     * Status code <code>421</code>, see <code>RFC7540</code> section <code>9.1.2</code>.
     */
    public static final StatusCode MISDIRECTED_REQUEST = new StatusCode(421);
    /**
     * Status code <code>422</code>, see <code>RFC4918</code>.
     */
    public static final StatusCode UNPROCESSABLE_ENTITY = new StatusCode(422);
    /**
     * Status code <code>423</code>, see <code>RFC4918</code>.
     */
    public static final StatusCode LOCKED = new StatusCode(423);
    /**
     * Status code <code>424</code>, see <code>RFC4918</code>.
     */
    public static final StatusCode FAILED_DEPENDENCY = new StatusCode(424);
    /**
     * Status code <code>425</code>, see <code>RFC8470</code>.
     */
    public static final StatusCode TOO_EARLY = new StatusCode(425);
    /**
     * Status code <code>426</code>, see <code>RFC7231</code> section <code>6.5.15</code>.
     */
    public static final StatusCode UPGRADE_REQUIRED = new StatusCode(426);
    /**
     * Status code <code>428</code>, see <code>RFC6585</code>.
     */
    public static final StatusCode PRECONDITION_REQUIRED = new StatusCode(428);
    /**
     * Status code <code>429</code>, see <code>RFC6585</code>.
     */
    public static final StatusCode TOO_MANY_REQUESTS = new StatusCode(429);
    /**
     * Status code <code>431</code>, see <code>RFC6585</code>.
     */
    public static final StatusCode REQUEST_HEADER_FIELDS_TOO_LARGE = new StatusCode(431);
    /**
     * Status code <code>451</code>, see <code>RFC7725</code>.
     */
    public static final StatusCode UNAVAILABLE_FOR_LEGAL_REASONS = new StatusCode(451);
    /**
     * Status code <code>500</code>, see <code>RFC7231</code> section <code>6.6.1</code>.
     */
    public static final StatusCode INTERNAL_SERVER_ERROR = new StatusCode(500);
    /**
     * Status code <code>501</code>, see <code>RFC7231</code> section <code>6.6.2</code>.
     */
    public static final StatusCode NOT_IMPLEMENTED = new StatusCode(501);
    /**
     * Status code <code>502</code>, see <code>RFC7231</code> section <code>6.6.3</code>.
     */
    public static final StatusCode BAD_GATEWAY = new StatusCode(502);
    /**
     * Status code <code>503</code>, see <code>RFC7231</code> section <code>6.6.4</code>.
     */
    public static final StatusCode SERVICE_UNAVAILABLE = new StatusCode(503);
    /**
     * Status code <code>504</code>, see <code>RFC7231</code> section <code>6.6.5</code>.
     */
    public static final StatusCode GATEWAY_TIMEOUT = new StatusCode(504);
    /**
     * Status code <code>505</code>, see <code>RFC7231</code> section <code>6.6.6</code>.
     */
    public static final StatusCode HTTP_VERSION_NOT_SUPPORTED = new StatusCode(505);
    /**
     * Status code <code>506</code>, see <code>RFC2295</code>.
     */
    public static final StatusCode VARIANT_ALSO_NEGOTIATES = new StatusCode(506);
    /**
     * Status code <code>507</code>, see <code>RFC4918</code>.
     */
    public static final StatusCode INSUFFICIENT_STORAGE = new StatusCode(507);
    /**
     * Status code <code>508</code>, see <code>RFC5842</code>.
     */
    public static final StatusCode LOOP_DETECTED = new StatusCode(508);
    /**
     * Status code <code>510</code>, see <code>RFC2774</code>.
     */
    public static final StatusCode NOT_EXTENDED = new StatusCode(510);
    /**
     * Status code <code>511</code>, see <code>RFC6585</code>.
     */
    public static final StatusCode NETWORK_AUTHENTICATION_REQUIRED = new StatusCode(511);
    private static final Map<Integer, StatusCode> KNOWN_CODES = new HashMap<>();
    private final int code;
    private final String codeString;

    private StatusCode(final int code) {
        this(code, true);
    }

    private StatusCode(final int code, final boolean register) {
        if (code <= 0) throw new IllegalArgumentException();
        this.code = code;
        this.codeString = "" + code;
        if (register) {
            KNOWN_CODES.put(code, this);
        }
    }

    /**
     * Returns status code integer value.
     * @return status code integer value
     */
    public int getCodeAsInteger() {
        return code;
    }

    /**
     * Returns status code integer value as string.
     * @return status code integer value as string
     */
    public String getCodeAsString() {
        return codeString;
    }

    /**
     * Creates specified status code.
     *
     * @param sc status code
     * @return status code instance
     * @throws IllegalArgumentException if status code is nonpositive integer
     * @throws NumberFormatException if the string does not contain an integer
     */
    public static StatusCode of(final String sc) {
        return of(Integer.parseInt(sc));
    }

    /**
     * Creates specified status code.
     *
     * @param sc status code
     * @return status code instance
     * @throws IllegalArgumentException if status code is nonpositive integer
     */
    public static StatusCode of(final int sc) {
        if (sc <= 0) throw new IllegalArgumentException();
        final StatusCode retVal = KNOWN_CODES.get(sc);
        return retVal != null ? retVal : new StatusCode(sc, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        return o == this || o instanceof StatusCode && code == ((StatusCode)o).code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Response status code: " + code;
    }

}
