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

import static org.fossnova.http2.Utils.validateHeaderName;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP message headers as defined by <a href="https://tools.ietf.org/html/rfc7540">HTTP 2</a> specification and
 * <a href="http://www.iana.org/assignments/message-headers/message-headers.xhtml">IANA</a> organization.
 *
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class Header {

    private static final Map<String, Header> KNOWN_HEADERS = new HashMap<>();
    /////////////////////////////
    // HTTP 2 specific headers //
    /////////////////////////////
    /**
     * <code>:authority</code> message header, see <code>RFC7540</code>.
     */
    public static final Header AUTHORITY = new Header(":authority", false);
    /**
     * <code>:method</code> message header, see <code>RFC7540</code>.
     */
    public static final Header METHOD = new Header(":method", false);
    /**
     * <code>:path</code> message header, see <code>RFC7540</code>.
     */
    public static final Header PATH = new Header(":path", false);
    /**
     * <code>:scheme</code> message header, see <code>RFC7540</code>.
     */
    public static final Header SCHEME = new Header(":scheme", false);
    /**
     * <code>:status</code> message header, see <code>RFC7540</code>.
     */
    public static final Header STATUS = new Header(":status", false);
    ///////////////////////////////
    // HTTP 1.1 specific headers //
    ///////////////////////////////
    /**
     * <code>A-IM</code> message header, see <code>RFC4229</code>.
     */
    public static final Header A_IM = new Header("A-IM");
    /**
     * <code>Accept</code> message header, see <code>RFC7231</code>.
     */
    public static final Header ACCEPT = new Header("Accept");
    /**
     * <code>Accept-Additions</code> message header, see <code>RFC4229</code>.
     */
    public static final Header ACCEPT_ADDITIONS = new Header("Accept-Additions");
    /**
     * <code>Accept-Charset</code> message header, see <code>RFC7231</code>.
     */
    public static final Header ACCEPT_CHARSET = new Header("Accept-Charset");
    /**
     * <code>Accept-Datetime</code> message header, see <code>RFC7089</code>.
     */
    public static final Header ACCEPT_DATETIME = new Header("Accept-Datetime");
    /**
     * <code>Accept-Encoding</code> message header, see <code>RFC7231</code> and <code>RFC7694</code>.
     */
    public static final Header ACCEPT_ENCODING = new Header("Accept-Encoding");
    /**
     * <code>Accept-Features</code> message header, see <code>RFC4229</code>.
     */
    public static final Header ACCEPT_FEATURES = new Header("Accept-Features");
    /**
     * <code>Accept-Language</code> message header, see <code>RFC7231</code>.
     */
    public static final Header ACCEPT_LANGUAGE = new Header("Accept-Language");
    /**
     * <code>Accept-Patch</code> message header, see <code>RFC5789</code>.
     */
    public static final Header ACCEPT_PATCH = new Header("Accept-Patch");
    /**
     * <code>Accept-Post</code> message header, see <code>https://www.w3.org/TR/ldp/</code>.
     */
    public static final Header ACCEPT_POST = new Header("Accept-Post");
    /**
     * <code>Accept-Ranges</code> message header, see <code>RFC7233</code>.
     */
    public static final Header ACCEPT_RANGES = new Header("Accept-Ranges");
    /**
     * <code>Access-Control</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final Header ACCESS_CONTROL = new Header("Access-Control");
    /**
     * <code>Access-Control-Allow-Credentials</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final Header ACCESS_CONTROL_ALLOW_CREDENTIALS = new Header("Access-Control-Allow-Credentials");
    /**
     * <code>Access-Control-Allow-Headers</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final Header ACCESS_CONTROL_ALLOW_HEADERS = new Header("Access-Control-Allow-Headers");
    /**
     * <code>Access-Control-Allow-Methods</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final Header ACCESS_CONTROL_ALLOW_METHODS = new Header("Access-Control-Allow-Methods");
    /**
     * <code>Access-Control-Allow-Origin</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final Header ACCESS_CONTROL_ALLOW_ORIGIN = new Header("Access-Control-Allow-Origin");
    /**
     * <code>Access-Control-Max-Age</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final Header ACCESS_CONTROL_MAX_AGE = new Header("Access-Control-Max-Age");
    /**
     * <code>Access-Control-Request-Method</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final Header ACCESS_CONTROL_REQUEST_METHOD = new Header("Access-Control-Request-Method");
    /**
     * <code>Access-Control-Request-Headers</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final Header ACCESS_CONTROL_REQUEST_HEADERS = new Header("Access-Control-Request-Headers");
    /**
     * <code>Age</code> message header, see <code>RFC7234</code>.
     */
    public static final Header AGE = new Header("Age");
    /**
     * <code>Allow</code> message header, see <code>RFC7231</code>.
     */
    public static final Header ALLOW = new Header("Allow");
    /**
     * <code>ALPN</code> message header, see <code>RFC7639</code>.
     */
    public static final Header ALPN = new Header("ALPN");
    /**
     * <code>Alt-Svc</code> message header, see <code>RFC7838</code>.
     */
    public static final Header ALT_SVC = new Header("Alt-Svc");
    /**
     * <code>Alt-Used</code> message header, see <code>RFC7838</code>.
     */
    public static final Header ALT_USED = new Header("Alt-Used");
    /**
     * <code>Alternates</code> message header, see <code>RFC4229</code>.
     */
    public static final Header ALTERNATES = new Header("Alternates");
    /**
     * <code>AMP-Cache-Transform</code> message header, see <code>https://github.com/ampproject/amphtml/blob/master/spec/amp-cache-transform.md</code>.
     */
    public static final Header AMP_CACHE_TRANSFORM = new Header("AMP-Cache-Transform");
    /**
     * <code>Apply-To-Redirect-Ref</code> message header, see <code>RFC4437</code>.
     */
    public static final Header APPLY_TO_REDIRECT_REF = new Header("Apply-To-Redirect-Ref");
    /**
     * <code>Authentication-Control</code> message header, see <code>RFC8053</code>.
     */
    public static final Header AUTHENTICATION_CONTROL = new Header("Authentication-Control");
    /**
     * <code>Authentication-Info</code> message header, see <code>RFC7615</code>.
     */
    public static final Header AUTHENTICATION_INFO = new Header("Authentication-Info");
    /**
     * <code>Authorization</code> message header, see <code>RFC7235</code>.
     */
    public static final Header AUTHORIZATION = new Header("Authorization");
    /**
     * <code>C-Ext</code> message header, see <code>RFC4229</code>.
     */
    public static final Header C_EXT = new Header("C-Ext");
    /**
     * <code>C-Man</code> message header, see <code>RFC4229</code>.
     */
    public static final Header C_MAN = new Header("C-Man");
    /**
     * <code>C-Opt</code> message header, see <code>RFC4229</code>.
     */
    public static final Header C_OPT = new Header("C-Opt");
    /**
     * <code>C-PEP</code> message header, see <code>RFC4229</code>.
     */
    public static final Header C_PEP = new Header("C-PEP");
    /**
     * <code>C-PEP-Info</code> message header, see <code>RFC4229</code>.
     */
    public static final Header C_PEP_INFO = new Header("C-PEP-Info");
    /**
     * <code>Cache-Control</code> message header, see <code>RFC7234</code>.
     */
    public static final Header CACHE_CONTROL = new Header("Cache-Control");
    /**
     * <code>Cal-Managed-ID</code> message header, see <code>RFC8607</code>.
     */
    public static final Header CAL_MANAGED_ID = new Header("Cal-Managed-ID");
    /**
     * <code>CalDAV-Timezones</code> message header, see <code>RFC7809</code>.
     */
    public static final Header CALDAV_TIMEZONES = new Header("CalDAV-Timezones");
    /**
     * <code>CDN-Loop</code> message header, see <code>RFC8586</code>.
     */
    public static final Header CDN_LOOP = new Header("CDN-Loop");
    /**
     * <code>Cert-Not-After</code> message header, see <code>RFC8739</code>.
     */
    public static final Header CERT_NOT_AFTER = new Header("Cert-Not-After");
    /**
     * <code>Cert-Not-Before</code> message header, see <code>RFC8739</code>.
     */
    public static final Header CERT_NOT_BEFORE = new Header("Cert-Not-Before");
    /**
     * <code>Close</code> message header, see <code>RFC7230</code>.
     */
    public static final Header CLOSE = new Header("Close");
    /**
     * <code>Connection</code> message header, see <code>RFC7230</code>.
     */
    public static final Header CONNECTION = new Header("Connection");
    /**
     * <code>Content-Base</code> message header, see <code>RFC2068</code> and <code>RFC2616</code>.
     */
    public static final Header CONTENT_BASE = new Header("Content-Base");
    /**
     * <code>Content-Disposition</code> message header, see <code>RFC6266</code>.
     */
    public static final Header CONTENT_DISPOSITION = new Header("Content-Disposition");
    /**
     * <code>Content-Encoding</code> message header, see <code>RFC7231</code>.
     */
    public static final Header CONTENT_ENCODING = new Header("Content-Encoding");
    /**
     * <code>Content-ID</code> message header, see <code>RFC4229</code>.
     */
    public static final Header CONTENT_ID = new Header("Content-ID");
    /**
     * <code>Content-Language</code> message header, see <code>RFC7231</code>.
     */
    public static final Header CONTENT_LANGUAGE = new Header("Content-Language");
    /**
     * <code>Content-Length</code> message header, see <code>RFC7230</code>.
     */
    public static final Header CONTENT_LENGTH = new Header("Content-Length");
    /**
     * <code>Content-Location</code> message header, see <code>RFC7231</code>.
     */
    public static final Header CONTENT_LOCATION = new Header("Content-Location");
    /**
     * <code>Content-MD5</code> message header, see <code>RFC4229</code>.
     */
    public static final Header CONTENT_MD5 = new Header("Content-MD5");
    /**
     * <code>Content-Range</code> message header, see <code>RFC7233</code>.
     */
    public static final Header CONTENT_RANGE = new Header("Content-Range");
    /**
     * <code>Content-Script-Type</code> message header, see <code>RFC4229</code>.
     */
    public static final Header CONTENT_SCRIPT_TYPE = new Header("Content-Script-Type");
    /**
     * <code>Content-Style-Type</code> message header, see <code>RFC4229</code>.
     */
    public static final Header CONTENT_STYLE_TYPE = new Header("Content-Style-Type");
    /**
     * <code>Content-Transfer-Encoding</code> message header, see <code>RFC4229</code>.
     */
    public static final Header CONTENT_TRANSFER_ENCODING = new Header("Content-Transfer-Encoding");
    /**
     * <code>Content-Type</code> message header, see <code>RFC7231</code>.
     */
    public static final Header CONTENT_TYPE = new Header("Content-Type");
    /**
     * <code>Content-Version</code> message header, see <code>RFC4229</code>.
     */
    public static final Header CONTENT_VERSION = new Header("Content-Version");
    /**
     * <code>Compliance</code> message header, see <code>RFC4229</code>.
     */
    public static final Header COMPLIANCE = new Header("Compliance");
    /**
     * <code>Cookie</code> message header, see <code>RFC6265</code>.
     */
    public static final Header COOKIE = new Header("Cookie");
    /**
     * <code>Cookie2</code> message header, see <code>RFC2965</code> and <code>RFC6265</code>.
     */
    public static final Header COOKIE2 = new Header("Cookie2");
    /**
     * <code>Cost</code> message header, see <code>RFC4229</code>.
     */
    public static final Header COST = new Header("Cost");
    /**
     * <code>DASL</code> message header, see <code>RFC5323</code>.
     */
    public static final Header DASL = new Header("DASL");
    /**
     * <code>DAV</code> message header, see <code>RFC4918</code>.
     */
    public static final Header DAV = new Header("DAV");
    /**
     * <code>Date</code> message header, see <code>RFC7231</code>.
     */
    public static final Header DATE = new Header("Date");
    /**
     * <code>Default-Style</code> message header, see <code>RFC4229</code>.
     */
    public static final Header DEFAULT_STYLE = new Header("Default-Style");
    /**
     * <code>Delta-Base</code> message header, see <code>RFC4229</code>.
     */
    public static final Header DELTA_BASE = new Header("Delta-Base");
    /**
     * <code>Depth</code> message header, see <code>RFC4918</code>.
     */
    public static final Header DEPTH = new Header("Depth");
    /**
     * <code>Derived-From</code> message header, see <code>RFC4229</code>.
     */
    public static final Header DERIVED_FROM = new Header("Derived-From");
    /**
     * <code>Destination</code> message header, see <code>RFC4918</code>.
     */
    public static final Header DESTINATION = new Header("Destination");
    /**
     * <code>Differential-ID</code> message header, see <code>RFC4229</code>.
     */
    public static final Header DIFFERENTIAL_ID = new Header("Differential-ID");
    /**
     * <code>Digest</code> message header, see <code>RFC4229</code>.
     */
    public static final Header DIGEST = new Header("Digest");
    /**
     * <code>Early-Data</code> message header, see <code>RFC8470</code>.
     */
    public static final Header EARLY_DATA = new Header("Early-Data");
    /**
     * <code>EDIINT-Features</code> message header, see <code>RFC6017</code>.
     */
    public static final Header EDIINT_FEATURES = new Header("EDIINT-Features");
    /**
     * <code>ETag</code> message header, see <code>RFC7232</code>.
     */
    public static final Header ETAG = new Header("ETag");
    /**
     * <code>Expect</code> message header, see <code>RFC7231</code>.
     */
    public static final Header EXPECT = new Header("Expect");
    /**
     * <code>Expect-CT</code> message header, see <code>RFC-ietf-httpbis-expect-ct-08</code>.
     */
    public static final Header EXPECT_CT = new Header("Expect-CT");
    /**
     * <code>Expires</code> message header, see <code>RFC7234</code>.
     */
    public static final Header EXPIRES = new Header("Expires");
    /**
     * <code>Ext</code> message header, see <code>RFC4229</code>.
     */
    public static final Header EXT = new Header("Ext");
    /**
     * <code>Forwarded</code> message header, see <code>RFC7239</code>.
     */
    public static final Header FORWARDED = new Header("Forwarded");
    /**
     * <code>From</code> message header, see <code>RFC7231</code>.
     */
    public static final Header FROM = new Header("From");
    /**
     * <code>GetProfile</code> message header, see <code>RFC4229</code>.
     */
    public static final Header GET_PROFILE = new Header("GetProfile");
    /**
     * <code>Hobareg</code> message header, see <code>RFC7486</code>.
     */
    public static final Header HOBAREG = new Header("Hobareg");
    /**
     * <code>Host</code> message header, see <code>RFC7230</code>.
     */
    public static final Header HOST = new Header("Host");
    /**
     * <code>HTTP2-Settings</code> message header, see <code>RFC7540</code>.
     */
    public static final Header HTTP2_SETTINGS = new Header("HTTP2-Settings");
    /**
     * <code>IM</code> message header, see <code>RFC4229</code>.
     */
    public static final Header IM = new Header("IM");
    /**
     * <code>If</code> message header, see <code>RFC4918</code>.
     */
    public static final Header IF = new Header("If");
    /**
     * <code>If-Match</code> message header, see <code>RFC7232</code>.
     */
    public static final Header IF_MATCH = new Header("If-Match");
    /**
     * <code>If-Modified-Since</code> message header, see <code>RFC7232</code>.
     */
    public static final Header IF_MODIFIED_SINCE = new Header("If-Modified-Since");
    /**
     * <code>If-None-Match</code> message header, see <code>RFC7232</code>.
     */
    public static final Header IF_NONE_MATCH = new Header("If-None-Match");
    /**
     * <code>If-Range</code> message header, see <code>RFC7233</code>.
     */
    public static final Header IF_RANGE = new Header("If-Range");
    /**
     * <code>If-Schedule-Tag-Match</code> message header, see <code>RFC6638</code>.
     */
    public static final Header IF_SCHEDULE_TAG_MATCH = new Header("If-Schedule-Tag-Match");
    /**
     * <code>If-Unmodified-Since</code> message header, see <code>RFC7232</code>.
     */
    public static final Header IF_UNMODIFIED_SINCE = new Header("If-Unmodified-Since");
    /**
     * <code>Include-Referred-Token-Binding-ID</code> message header, see <code>RFC8473</code>.
     */
    public static final Header INCLUDE_REFERRED_TOKEN_BINDING_ID = new Header("Include-Referred-Token-Binding-ID");
    /**
     * <code>Isolation</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final Header ISOLATION = new Header("Isolation");
    /**
     * <code>Keep-Alive</code> message header, see <code>RFC4229</code>.
     */
    public static final Header KEEP_ALIVE = new Header("Keep-Alive");
    /**
     * <code>Label</code> message header, see <code>RFC4229</code>.
     */
    public static final Header LABEL = new Header("Label");
    /**
     * <code>Last-Modified</code> message header, see <code>RFC7232</code>.
     */
    public static final Header LAST_MODIFIED = new Header("Last-Modified");
    /**
     * <code>Link</code> message header, see <code>RFC8288</code>.
     */
    public static final Header LINK = new Header("Link");
    /**
     * <code>Location</code> message header, see <code>RFC7231</code>.
     */
    public static final Header LOCATION = new Header("Location");
    /**
     * <code>Lock-Token</code> message header, see <code>RFC4918</code>.
     */
    public static final Header LOCK_TOKEN = new Header("Lock-Token");
    /**
     * <code>Man</code> message header, see <code>RFC4229</code>.
     */
    public static final Header MAN = new Header("Man");
    /**
     * <code>Max-Forwards</code> message header, see <code>RFC7231</code>.
     */
    public static final Header MAX_FORWARDS = new Header("Max-Forwards");
    /**
     * <code>Memento-Datetime</code> message header, see <code>RFC7089</code>.
     */
    public static final Header MEMENTO_DATETIME = new Header("Memento-Datetime");
    /**
     * <code>Message-ID</code> message header, see <code>RFC4229</code>.
     */
    public static final Header MESSAGE_ID = new Header("Message-ID");
    /**
     * <code>Meter</code> message header, see <code>RFC4229</code>.
     */
    public static final Header METER = new Header("Meter");
    /**
     * <code>Method-Check</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final Header METHOD_CHECK = new Header("Method-Check");
    /**
     * <code>Method-Check-Expires</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final Header METHOD_CHECK_EXPIRES = new Header("Method-Check-Expires");
    /**
     * <code>MIME-Version</code> message header, see <code>RFC7231</code>.
     */
    public static final Header MIME_VERSION = new Header("MIME-Version");
    /**
     * <code>Negotiate</code> message header, see <code>RFC4229</code>.
     */
    public static final Header NEGOTIATE = new Header("Negotiate");
    /**
     * <code>Non-Compliance</code> message header, see <code>RFC4229</code>.
     */
    public static final Header NON_COMPLIANCE = new Header("Non-Compliance");
    /**
     * <code>OData-EntityId</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final Header ODATA_ENTITY_ID = new Header("OData-EntityId");
    /**
     * <code>OData-Isolation</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final Header ODATA_ISOLATION = new Header("OData-Isolation");
    /**
     * <code>OData-MaxVersion</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final Header ODATA_MAX_VERSION = new Header("OData-MaxVersion");
    /**
     * <code>OData-Version</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final Header ODATA_VERSION = new Header("OData-Version");
    /**
     * <code>Opt</code> message header, see <code>RFC4229</code>.
     */
    public static final Header OPT = new Header("Opt");
    /**
     * <code>Optional</code> message header, see <code>RFC4229</code>.
     */
    public static final Header OPTIONAL = new Header("Optional");
    /**
     * <code>Optional-WWW-Authenticate</code> message header, see <code>RFC8053</code>.
     */
    public static final Header OPTIONAL_WWW_AUTHENTICATE = new Header("Optional-WWW-Authenticate");
    /**
     * <code>Ordering-Type</code> message header, see <code>RFC4229</code>.
     */
    public static final Header ORDERING_TYPE = new Header("Ordering-Type");
    /**
     * <code>Origin</code> message header, see <code>RFC6454</code>.
     */
    public static final Header ORIGIN = new Header("Origin");
    /**
     * <code>OSCORE</code> message header, see <code>RFC8613</code>.
     */
    public static final Header OSCORE = new Header("OSCORE");
    /**
     * <code>Overwrite</code> message header, see <code>RFC4918</code>.
     */
    public static final Header OVERWRITE = new Header("Overwrite");
    /**
     * <code>P3P</code> message header, see <code>RFC4229</code>.
     */
    public static final Header P3P = new Header("P3P");
    /**
     * <code>PEP</code> message header, see <code>RFC4229</code>.
     */
    public static final Header PEP = new Header("PEP");
    /**
     * <code>PICS-Label</code> message header, see <code>RFC4229</code>.
     */
    public static final Header PICS_LABEL = new Header("PICS-Label");
    /**
     * <code>Pep-Info</code> message header, see <code>RFC4229</code>.
     */
    public static final Header PEP_INFO = new Header("Pep-Info");
    /**
     * <code>Position</code> message header, see <code>RFC4229</code>.
     */
    public static final Header POSITION = new Header("Position");
    /**
     * <code>Pragma</code> message header, see <code>RFC7234</code>.
     */
    public static final Header PRAGMA = new Header("Pragma");
    /**
     * <code>Prefer</code> message header, see <code>RFC7240</code>.
     */
    public static final Header PREFER = new Header("Prefer");
    /**
     * <code>Preference-Applied</code> message header, see <code>RFC7240</code>.
     */
    public static final Header PREFERENCE_APPLIED = new Header("Preference-Applied");
    /**
     * <code>ProfileObject</code> message header, see <code>RFC4229</code>.
     */
    public static final Header PROFILE_OBJECT = new Header("ProfileObject");
    /**
     * <code>Protocol</code> message header, see <code>RFC4229</code>.
     */
    public static final Header PROTOCOL = new Header("Protocol");
    /**
     * <code>Protocol-Info</code> message header, see <code>RFC4229</code>.
     */
    public static final Header PROTOCOL_INFO = new Header("Protocol-Info");
    /**
     * <code>Protocol-Query</code> message header, see <code>RFC4229</code>.
     */
    public static final Header PROTOCOL_QUERY = new Header("Protocol-Query");
    /**
     * <code>Protocol-Request</code> message header, see <code>RFC4229</code>.
     */
    public static final Header PROTOCOL_REQUEST = new Header("Protocol-Request");
    /**
     * <code>Proxy-Authenticate</code> message header, see <code>RFC7235</code>.
     */
    public static final Header PROXY_AUTHENTICATE = new Header("Proxy-Authenticate");
    /**
     * <code>Proxy-Authentication-Info</code> message header, see <code>RFC7615</code>.
     */
    public static final Header PROXY_AUTHENTICATION_INFO = new Header("Proxy-Authentication-Info");
    /**
     * <code>Proxy-Authorization</code> message header, see <code>RFC7235</code>.
     */
    public static final Header PROXY_AUTHORIZATION = new Header("Proxy-Authorization");
    /**
     * <code>Proxy-Features</code> message header, see <code>RFC4229</code>.
     */
    public static final Header PROXY_FEATURES = new Header("Proxy-Features");
    /**
     * <code>Proxy-Instruction</code> message header, see <code>RFC4229</code>.
     */
    public static final Header PROXY_INSTRUCTION = new Header("Proxy-Instruction");
    /**
     * <code>Public</code> message header, see <code>RFC4229</code>.
     */
    public static final Header PUBLIC = new Header("Public");
    /**
     * <code>Public-Key-Pins</code> message header, see <code>RFC7469</code>.
     */
    public static final Header PUBLIC_KEY_PINS = new Header("Public-Key-Pins");
    /**
     * <code>Public-Key-Pins-Report-Only</code> message header, see <code>RFC7469</code>.
     */
    public static final Header PUBLIC_KEY_PINS_REPORT_ONLY = new Header("Public-Key-Pins-Report-Only");
    /**
     * <code>Range</code> message header, see <code>RFC7233</code>.
     */
    public static final Header RANGE = new Header("Range");
    /**
     * <code>Redirect-Ref</code> message header, see <code>RFC4437</code>.
     */
    public static final Header REDIRECT_REF = new Header("Redirect-Ref");
    /**
     * <code>Referer</code> message header, see <code>RFC7231</code>.
     */
    public static final Header REFERER = new Header("Referer");
    /**
     * <code>Referer-Root</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final Header REFERER_ROOT = new Header("Referer-Root");
    /**
     * <code>Refresh</code> message header, see <code>https://html.spec.whatwg.org/multipage/browsing-the-web.html#navigating-across-documents%3Ashared-declarative-refresh-steps</code>.
     */
    public static final Header REFRESH = new Header("Refresh");
    /**
     * <code>Replay-Nonce</code> message header, see <code>RFC8555</code>.
     */
    public static final Header REPLAY_NONCE = new Header("Replay-Nonce");
    /**
     * <code>Resolution-Hint</code> message header, see <code>RFC4229</code>.
     */
    public static final Header RESOLUTION_HINT = new Header("Resolution-Hint");
    /**
     * <code>Resolver-Location</code> message header, see <code>RFC4229</code>.
     */
    public static final Header RESOLVER_LOCATION = new Header("Resolver-Location");
    /**
     * <code>Retry-After</code> message header, see <code>RFC7231</code>.
     */
    public static final Header RETRY_AFTER = new Header("Retry-After");
    /**
     * <code>Safe</code> message header, see <code>RFC4229</code>.
     */
    public static final Header SAFE = new Header("Safe");
    /**
     * <code>Schedule-Reply</code> message header, see <code>RFC6638</code>.
     */
    public static final Header SCHEDULE_REPLY = new Header("Schedule-Reply");
    /**
     * <code>Schedule-Tag</code> message header, see <code>RFC6638</code>.
     */
    public static final Header SCHEDULE_TAG = new Header("Schedule-Tag");
    /**
     * <code>Sec-Token-Binding</code> message header, see <code>RFC8473</code>.
     */
    public static final Header SEC_TOKEN_BINDING = new Header("Sec-Token-Binding");
    /**
     * <code>Sec-WebSocket-Accept</code> message header, see <code>RFC6455</code>.
     */
    public static final Header SEC_WEBSOCKET_ACCEPT = new Header("Sec-WebSocket-Accept");
    /**
     * <code>Sec-WebSocket-Extensions</code> message header, see <code>RFC6455</code>.
     */
    public static final Header SEC_WEBSOCKET_EXTENSIONS = new Header("Sec-WebSocket-Extensions");
    /**
     * <code>Sec-WebSocket-Key</code> message header, see <code>RFC6455</code>.
     */
    public static final Header SEC_WEBSOCKET_KEY = new Header("Sec-WebSocket-Key");
    /**
     * <code>Sec-WebSocket-Protocol</code> message header, see <code>RFC6455</code>.
     */
    public static final Header SEC_WEBSOCKET_PROTOCOL = new Header("Sec-WebSocket-Protocol");
    /**
     * <code>Sec-WebSocket-Version</code> message header, see <code>RFC6455</code>.
     */
    public static final Header SEC_WEBSOCKET_VERSION = new Header("Sec-WebSocket-Version");
    /**
     * <code>Security-Scheme</code> message header, see <code>RFC4229</code>.
     */
    public static final Header SECURITY_SCHEME = new Header("Security-Scheme");
    /**
     * <code>Server</code> message header, see <code>RFC7231</code>.
     */
    public static final Header SERVER = new Header("Server");
    /**
     * <code>Set-Cookie</code> message header, see <code>RFC6265</code>.
     */
    public static final Header SET_COOKIE = new Header("Set-Cookie");
    /**
     * <code>Set-Cookie2</code> message header, see <code>RFC2965</code> and <code>RFC6265</code>.
     */
    public static final Header SET_COOKIE2 = new Header("Set-Cookie2");
    /**
     * <code>SetProfile</code> message header, see <code>RFC4229</code>.
     */
    public static final Header SET_PROFILE = new Header("SetProfile");
    /**
     * <code>SLUG</code> message header, see <code>RFC5023</code>.
     */
    public static final Header SLUG = new Header("SLUG");
    /**
     * <code>SoapAction</code> message header, see <code>RFC4229</code>.
     */
    public static final Header SOAP_ACTION = new Header("SoapAction");
    /**
     * <code>Status-URI</code> message header, see <code>RFC4229</code>.
     */
    public static final Header STATUS_URI = new Header("Status-URI");
    /**
     * <code>Strict-Transport-Security</code> message header, see <code>RFC6797</code>.
     */
    public static final Header STRICT_TRANSPORT_SECURITY = new Header("Strict-Transport-Security");
    /**
     * <code>SubOK</code> message header, see <code>RFC4229</code>.
     */
    public static final Header SUBOK = new Header("SubOK");
    /**
     * <code>Subst</code> message header, see <code>RFC4229</code>.
     */
    public static final Header SUBST = new Header("Subst");
    /**
     * <code>Sunset</code> message header, see <code>RFC8594</code>.
     */
    public static final Header SUNSET = new Header("Sunset");
    /**
     * <code>Surrogate-Capability</code> message header, see <code>RFC4229</code>.
     */
    public static final Header SURROGATE_CAPABILITY = new Header("Surrogate-Capability");
    /**
     * <code>Surrogate-Control</code> message header, see <code>RFC4229</code>.
     */
    public static final Header SURROGATE_CONTROL = new Header("Surrogate-Control");
    /**
     * <code>TCN</code> message header, see <code>RFC4229</code>.
     */
    public static final Header TCN = new Header("TCN");
    /**
     * <code>TE</code> message header, see <code>RFC7230</code>.
     */
    public static final Header TE = new Header("TE");
    /**
     * <code>Timeout</code> message header, see <code>RFC4918</code>.
     */
    public static final Header TIMEOUT = new Header("Timeout");
    /**
     * <code>Timing-Allow-Origin</code> message header, see <code>https://www.w3.org/TR/resource-timing-1/#timing-allow-origin</code>.
     */
    public static final Header TIMING_ALLOW_ORIGIN = new Header("Timing-Allow-Origin");
    /**
     * <code>Title</code> message header, see <code>RFC4229</code>.
     */
    public static final Header TITLE = new Header("Title");
    /**
     * <code>Topic</code> message header, see <code>RFC8030</code>.
     */
    public static final Header TOPIC = new Header("Topic");
    /**
     * <code>Traceparent</code> message header, see <code>https://www.w3.org/TR/trace-context/#traceparent-field</code>.
     */
    public static final Header TRACEPARENT = new Header("Traceparent");
    /**
     * <code>Tracestate</code> message header, see <code>https://www.w3.org/TR/trace-context/#tracestate-field</code>.
     */
    public static final Header TRACESTATE = new Header("Tracestate");
    /**
     * <code>Trailer</code> message header, see <code>RFC7230</code>.
     */
    public static final Header TRAILER = new Header("Trailer");
    /**
     * <code>Transfer-Encoding</code> message header, see <code>RFC7230</code>.
     */
    public static final Header TRANSFER_ENCODING = new Header("Transfer-Encoding");
    /**
     * <code>TTL</code> message header, see <code>RFC8030</code>.
     */
    public static final Header TTL = new Header("TTL");
    /**
     * <code>UA-Color</code> message header, see <code>RFC4229</code>.
     */
    public static final Header UA_COLOR = new Header("UA-Color");
    /**
     * <code>UA-Media</code> message header, see <code>RFC4229</code>.
     */
    public static final Header UA_MEDIA = new Header("UA-Media");
    /**
     * <code>UA-Pixels</code> message header, see <code>RFC4229</code>.
     */
    public static final Header UA_PIXELS = new Header("UA-Pixels");
    /**
     * <code>UA-Resolution</code> message header, see <code>RFC4229</code>.
     */
    public static final Header UA_RESOLUTION = new Header("UA-Resolution");
    /**
     * <code>UA-Windowpixels</code> message header, see <code>RFC4229</code>.
     */
    public static final Header UA_WINDOWPIXELS = new Header("UA-Windowpixels");
    /**
     * <code>Urgency</code> message header, see <code>RFC8030</code>.
     */
    public static final Header URGENCY = new Header("Urgency");
    /**
     * <code>URI</code> message header, see <code>RFC4229</code>.
     */
    public static final Header URI = new Header("URI");
    /**
     * <code>Upgrade</code> message header, see <code>RFC7230</code>.
     */
    public static final Header UPGRADE = new Header("Upgrade");
    /**
     * <code>User-Agent</code> message header, see <code>RFC7231</code>.
     */
    public static final Header USER_AGENT = new Header("User-Agent");
    /**
     * <code>Variant-Vary</code> message header, see <code>RFC4229</code>.
     */
    public static final Header VARIANT_VARY = new Header("Variant-Vary");
    /**
     * <code>Vary</code> message header, see <code>RFC7231</code>.
     */
    public static final Header VARY = new Header("Vary");
    /**
     * <code>Via</code> message header, see <code>RFC7230</code>.
     */
    public static final Header VIA = new Header("Via");
    /**
     * <code>Version</code> message header, see <code>RFC4229</code>.
     */
    public static final Header VERSION = new Header("Version");
    /**
     * <code>WWW-Authenticate</code> message header, see <code>RFC7235</code>.
     */
    public static final Header WWW_AUTHENTICATE = new Header("WWW-Authenticate");
    /**
     * <code>Want-Digest</code> message header, see <code>RFC4229</code>.
     */
    public static final Header WANT_DIGEST = new Header("Want-Digest");
    /**
     * <code>Warning</code> message header, see <code>RFC7234</code>.
     */
    public static final Header WARNING = new Header("Warning");
    /**
     * <code>X-Content-Type-Options</code> message header, see <code>RFC7230</code>.
     */
    public static final Header X_CONTENT_TYPE_OPTIONS = new Header("X-Content-Type-Options");
    /**
     * <code>X-Device-Accept</code> message header, see <code>W3C Mobile Web Best Practices Working Group</code>.
     */
    public static final Header X_DEVICE_ACCEPT = new Header("X-Device-Accept");
    /**
     * <code>X-Device-Accept-Charset</code> message header, see <code>W3C Mobile Web Best Practices Working Group</code>.
     */
    public static final Header X_DEVICE_ACCEPT_CHARSET = new Header("X-Device-Accept-Charset");
    /**
     * <code>X-Device-Accept-Encoding</code> message header, see <code>W3C Mobile Web Best Practices Working Group</code>.
     */
    public static final Header X_DEVICE_ACCEPT_ENCODING = new Header("X-Device-Accept-Encoding");
    /**
     * <code>X-Device-Accept-Language</code> message header, see <code>W3C Mobile Web Best Practices Working Group</code>.
     */
    public static final Header X_DEVICE_ACCEPT_LANGUAGE = new Header("X-Device-Accept-Language");
    /**
     * <code>X-Device-User-Agent</code> message header, see <code>W3C Mobile Web Best Practices Working Group</code>.
     */
    public static final Header X_DEVICE_USER_AGENT = new Header("X-Device-User-Agent");
    /**
     * <code>X-Frame-Options</code> message header, see <code>RFC7034</code>.
     */
    public static final Header X_FRAME_OPTIONS = new Header("X-Frame-Options");

    private final String titleCaseName;
    private final String lowerCaseName;

    private Header(final String name) {
        this(name, true, true);
    }

    private Header(final String name, final boolean validate) {
        this(name, validate, true);
    }

    private Header(final String name, final boolean validate, final boolean register) {
        if (validate) {
            validateHeaderName(name);
        }
        this.titleCaseName = name;
        this.lowerCaseName = name.toLowerCase();
        if (register) {
            KNOWN_HEADERS.put(lowerCaseName, this);
        }
    }

    /**
     * Returns message header name as defined by HTTP specifications.
     * @return message header name in its original form
     */
    public String getTitleCaseName() {
        return titleCaseName;
    }

    /**
     * Returns message header name with all letters de-capitalized.
     * @return message header name in lower case form
     */
    public String getLowerCaseName() {
        return lowerCaseName;
    }

    /**
     * Creates specified message header.
     * @param name header name
     * @return message header instance
     * @throws IllegalArgumentException if message header name doesn't match HTTP's spec. <code>token</code> definition
     */
    public static Header of(final String name) {
        validateHeaderName(name);
        final Header retVal = KNOWN_HEADERS.get(name.toLowerCase());
        return retVal != null ? retVal : new Header(name, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        return o == this || o instanceof Header && lowerCaseName.equals(((Header)o).lowerCaseName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return lowerCaseName.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Request header name: " + titleCaseName;
    }

}
