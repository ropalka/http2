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

import static org.fossnova.http2.Utils.validateToken;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * HTTP message headers as defined by
 * <a href="http://www.iana.org/assignments/message-headers/message-headers.xhtml">IANA</a> organization.
 *
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class MessageHeader {

    /**
     * <code>A-IM</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader A_IM = new MessageHeader("A-IM");
    /**
     * <code>Accept</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader ACCEPT = new MessageHeader("Accept");
    /**
     * <code>Accept-Additions</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader ACCEPT_ADDITIONS = new MessageHeader("Accept-Additions");
    /**
     * <code>Accept-Charset</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader ACCEPT_CHARSET = new MessageHeader("Accept-Charset");
    /**
     * <code>Accept-Datetime</code> message header, see <code>RFC7089</code>.
     */
    public static final MessageHeader ACCEPT_DATETIME = new MessageHeader("Accept-Datetime");
    /**
     * <code>Accept-Encoding</code> message header, see <code>RFC7231</code> and <code>RFC7694</code>.
     */
    public static final MessageHeader ACCEPT_ENCODING = new MessageHeader("Accept-Encoding");
    /**
     * <code>Accept-Features</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader ACCEPT_FEATURES = new MessageHeader("Accept-Features");
    /**
     * <code>Accept-Language</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader ACCEPT_LANGUAGE = new MessageHeader("Accept-Language");
    /**
     * <code>Accept-Patch</code> message header, see <code>RFC5789</code>.
     */
    public static final MessageHeader ACCEPT_PATCH = new MessageHeader("Accept-Patch");
    /**
     * <code>Accept-Post</code> message header, see <code>https://www.w3.org/TR/ldp/</code>.
     */
    public static final MessageHeader ACCEPT_POST = new MessageHeader("Accept-Post");
    /**
     * <code>Accept-Ranges</code> message header, see <code>RFC7233</code>.
     */
    public static final MessageHeader ACCEPT_RANGES = new MessageHeader("Accept-Ranges");
    /**
     * <code>Age</code> message header, see <code>RFC7234</code>.
     */
    public static final MessageHeader AGE = new MessageHeader("Age");
    /**
     * <code>Allow</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader ALLOW = new MessageHeader("Allow");
    /**
     * <code>ALPN</code> message header, see <code>RFC7639</code>.
     */
    public static final MessageHeader ALPN = new MessageHeader("ALPN");
    /**
     * <code>Alt-Svc</code> message header, see <code>RFC7838</code>.
     */
    public static final MessageHeader ALT_SVC = new MessageHeader("Alt-Svc");
    /**
     * <code>Alt-Used</code> message header, see <code>RFC7838</code>.
     */
    public static final MessageHeader ALT_USED = new MessageHeader("Alt-Used");
    /**
     * <code>Alternates</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader ALTERNATES = new MessageHeader("Alternates");
    /**
     * <code>Apply-To-Redirect-Ref</code> message header, see <code>RFC4437</code>.
     */
    public static final MessageHeader APPLY_TO_REDIRECT_REF = new MessageHeader("Apply-To-Redirect-Ref");
    /**
     * <code>Authentication-Control</code> message header, see <code>RFC8053</code>.
     */
    public static final MessageHeader AUTHENTICATION_CONTROL = new MessageHeader("Authentication-Control");
    /**
     * <code>Authentication-Info</code> message header, see <code>RFC7615</code>.
     */
    public static final MessageHeader AUTHENTICATION_INFO = new MessageHeader("Authentication-Info");
    /**
     * <code>Authorization</code> message header, see <code>RFC7235</code>.
     */
    public static final MessageHeader AUTHORIZATION = new MessageHeader("Authorization");
    /**
     * <code>C-Ext</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader C_EXT = new MessageHeader("C-Ext");
    /**
     * <code>C-Man</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader C_MAN = new MessageHeader("C-Man");
    /**
     * <code>C-Opt</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader C_OPT = new MessageHeader("C-Opt");
    /**
     * <code>C-PEP</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader C_PEP = new MessageHeader("C-PEP");
    /**
     * <code>C-PEP-Info</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader C_PEP_INFO = new MessageHeader("C-PEP-Info");
    /**
     * <code>Cache-Control</code> message header, see <code>RFC7234</code>.
     */
    public static final MessageHeader CACHE_CONTROL = new MessageHeader("Cache-Control");
    /**
     * <code>Cal-Managed-ID</code> message header, see <code>RFC8607</code>.
     */
    public static final MessageHeader CAL_MANAGED_ID = new MessageHeader("Cal-Managed-ID");
    /**
     * <code>CalDAV-Timezones</code> message header, see <code>RFC7809</code>.
     */
    public static final MessageHeader CALDAV_TIMEZONES = new MessageHeader("CalDAV-Timezones");
    /**
     * <code>CDN-Loop</code> message header, see <code>RFC8586</code>.
     */
    public static final MessageHeader CDN_LOOP = new MessageHeader("CDN-Loop");
    /**
     * <code>Cert-Not-After</code> message header, see <code>RFC8739</code>.
     */
    public static final MessageHeader CERT_NOT_AFTER = new MessageHeader("Cert-Not-After");
    /**
     * <code>Cert-Not-Before</code> message header, see <code>RFC8739</code>.
     */
    public static final MessageHeader CERT_NOT_BEFORE = new MessageHeader("Cert-Not-Before");
    /**
     * <code>Close</code> message header, see <code>RFC7230</code>.
     */
    public static final MessageHeader CLOSE = new MessageHeader("Close");
    /**
     * <code>Connection</code> message header, see <code>RFC7230</code>.
     */
    public static final MessageHeader CONNECTION = new MessageHeader("Connection");
    /**
     * <code>Content-Base</code> message header, see <code>RFC2068</code> and <code>RFC2616</code>.
     */
    public static final MessageHeader CONTENT_BASE = new MessageHeader("Content-Base");
    /**
     * <code>Content-Disposition</code> message header, see <code>RFC6266</code>.
     */
    public static final MessageHeader CONTENT_DISPOSITION = new MessageHeader("Content-Disposition");
    /**
     * <code>Content-Encoding</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader CONTENT_ENCODING = new MessageHeader("Content-Encoding");
    /**
     * <code>Content-ID</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader CONTENT_ID = new MessageHeader("Content-ID");
    /**
     * <code>Content-Language</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader CONTENT_LANGUAGE = new MessageHeader("Content-Language");
    /**
     * <code>Content-Length</code> message header, see <code>RFC7230</code>.
     */
    public static final MessageHeader CONTENT_LENGTH = new MessageHeader("Content-Length");
    /**
     * <code>Content-Location</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader CONTENT_LOCATION = new MessageHeader("Content-Location");
    /**
     * <code>Content-MD5</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader CONTENT_MD5 = new MessageHeader("Content-MD5");
    /**
     * <code>Content-Range</code> message header, see <code>RFC7233</code>.
     */
    public static final MessageHeader CONTENT_RANGE = new MessageHeader("Content-Range");
    /**
     * <code>Content-Script-Type</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader CONTENT_SCRIPT_TYPE = new MessageHeader("Content-Script-Type");
    /**
     * <code>Content-Style-Type</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader CONTENT_STYLE_TYPE = new MessageHeader("Content-Style-Type");
    /**
     * <code>Content-Type</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader CONTENT_TYPE = new MessageHeader("Content-Type");
    /**
     * <code>Content-Version</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader CONTENT_VERSION = new MessageHeader("Content-Version");
    /**
     * <code>Cookie</code> message header, see <code>RFC6265</code>.
     */
    public static final MessageHeader COOKIE = new MessageHeader("Cookie");
    /**
     * <code>Cookie2</code> message header, see <code>RFC2965</code> and <code>RFC6265</code>.
     */
    public static final MessageHeader COOKIE2 = new MessageHeader("Cookie2");
    /**
     * <code>DASL</code> message header, see <code>RFC5323</code>.
     */
    public static final MessageHeader DASL = new MessageHeader("DASL");
    /**
     * <code>DAV</code> message header, see <code>RFC4918</code>.
     */
    public static final MessageHeader DAV = new MessageHeader("DAV");
    /**
     * <code>Date</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader DATE = new MessageHeader("Date");
    /**
     * <code>Default-Style</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader DEFAULT_STYLE = new MessageHeader("Default-Style");
    /**
     * <code>Delta-Base</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader DELTA_BASE = new MessageHeader("Delta-Base");
    /**
     * <code>Depth</code> message header, see <code>RFC4918</code>.
     */
    public static final MessageHeader DEPTH = new MessageHeader("Depth");
    /**
     * <code>Derived-From</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader DERIVED_FROM = new MessageHeader("Derived-From");
    /**
     * <code>Destination</code> message header, see <code>RFC4918</code>.
     */
    public static final MessageHeader DESTINATION = new MessageHeader("Destination");
    /**
     * <code>Differential-ID</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader DIFFERENTIAL_ID = new MessageHeader("Differential-ID");
    /**
     * <code>Digest</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader DIGEST = new MessageHeader("Digest");
    /**
     * <code>Early-Data</code> message header, see <code>RFC8470</code>.
     */
    public static final MessageHeader EARLY_DATA = new MessageHeader("Early-Data");
    /**
     * <code>ETag</code> message header, see <code>RFC7232</code>.
     */
    public static final MessageHeader ETAG = new MessageHeader("ETag");
    /**
     * <code>Expect</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader EXPECT = new MessageHeader("Expect");
    /**
     * <code>Expect-CT</code> message header, see <code>RFC-ietf-httpbis-expect-ct-08</code>.
     */
    public static final MessageHeader EXPECT_CT = new MessageHeader("Expect-CT");
    /**
     * <code>Expires</code> message header, see <code>RFC7234</code>.
     */
    public static final MessageHeader EXPIRES = new MessageHeader("Expires");
    /**
     * <code>Ext</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader EXT = new MessageHeader("Ext");
    /**
     * <code>Forwarded</code> message header, see <code>RFC7239</code>.
     */
    public static final MessageHeader FORWARDED = new MessageHeader("Forwarded");
    /**
     * <code>From</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader FROM = new MessageHeader("From");
    /**
     * <code>GetProfile</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader GET_PROFILE = new MessageHeader("GetProfile");
    /**
     * <code>Hobareg</code> message header, see <code>RFC7486</code>.
     */
    public static final MessageHeader HOBAREG = new MessageHeader("Hobareg");
    /**
     * <code>Host</code> message header, see <code>RFC7230</code>.
     */
    public static final MessageHeader HOST = new MessageHeader("Host");
    /**
     * <code>HTTP2-Settings</code> message header, see <code>RFC7540</code>.
     */
    public static final MessageHeader HTTP2_SETTINGS = new MessageHeader("HTTP2-Settings");

    private static final Map<String, MessageHeader> KNOWN_HEADERS = new HashMap<>();

    private final String name;

    private MessageHeader(final String name) {
        this(name, true);
    }

    private MessageHeader(final String name, final boolean register) {
        this.name = name;
        if (register) {
            KNOWN_HEADERS.put(name.toLowerCase(Locale.US), this);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Creates specified message header.
     *
     * @param name header name
     * @return message header instance
     * @throws IllegalArgumentException if message header name doesn't match HTTP's spec. <code>token</code> definition
     */
    public static MessageHeader of(final String name) {
        validateToken(name);
        MessageHeader retVal = KNOWN_HEADERS.get(name.toLowerCase(Locale.US));
        return retVal != null ? retVal : new MessageHeader(name, false);
    }

}
