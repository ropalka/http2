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
public final class HeaderName {

    /**
     * <code>A-IM</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName A_IM = new HeaderName("A-IM");
    /**
     * <code>Accept</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName ACCEPT = new HeaderName("Accept");
    /**
     * <code>Accept-Additions</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName ACCEPT_ADDITIONS = new HeaderName("Accept-Additions");
    /**
     * <code>Accept-Charset</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName ACCEPT_CHARSET = new HeaderName("Accept-Charset");
    /**
     * <code>Accept-Datetime</code> message header, see <code>RFC7089</code>.
     */
    public static final HeaderName ACCEPT_DATETIME = new HeaderName("Accept-Datetime");
    /**
     * <code>Accept-Encoding</code> message header, see <code>RFC7231</code> and <code>RFC7694</code>.
     */
    public static final HeaderName ACCEPT_ENCODING = new HeaderName("Accept-Encoding");
    /**
     * <code>Accept-Features</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName ACCEPT_FEATURES = new HeaderName("Accept-Features");
    /**
     * <code>Accept-Language</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName ACCEPT_LANGUAGE = new HeaderName("Accept-Language");
    /**
     * <code>Accept-Patch</code> message header, see <code>RFC5789</code>.
     */
    public static final HeaderName ACCEPT_PATCH = new HeaderName("Accept-Patch");
    /**
     * <code>Accept-Post</code> message header, see <code>https://www.w3.org/TR/ldp/</code>.
     */
    public static final HeaderName ACCEPT_POST = new HeaderName("Accept-Post");
    /**
     * <code>Accept-Ranges</code> message header, see <code>RFC7233</code>.
     */
    public static final HeaderName ACCEPT_RANGES = new HeaderName("Accept-Ranges");
    /**
     * <code>Access-Control</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final HeaderName ACCESS_CONTROL = new HeaderName("Access-Control");
    /**
     * <code>Access-Control-Allow-Credentials</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final HeaderName ACCESS_CONTROL_ALLOW_CREDENTIALS = new HeaderName("Access-Control-Allow-Credentials");
    /**
     * <code>Access-Control-Allow-Headers</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final HeaderName ACCESS_CONTROL_ALLOW_HEADERS = new HeaderName("Access-Control-Allow-Headers");
    /**
     * <code>Access-Control-Allow-Methods</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final HeaderName ACCESS_CONTROL_ALLOW_METHODS = new HeaderName("Access-Control-Allow-Methods");
    /**
     * <code>Access-Control-Allow-Origin</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final HeaderName ACCESS_CONTROL_ALLOW_ORIGIN = new HeaderName("Access-Control-Allow-Origin");
    /**
     * <code>Access-Control-Max-Age</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final HeaderName ACCESS_CONTROL_MAX_AGE = new HeaderName("Access-Control-Max-Age");
    /**
     * <code>Access-Control-Request-Method</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final HeaderName ACCESS_CONTROL_REQUEST_METHOD = new HeaderName("Access-Control-Request-Method");
    /**
     * <code>Access-Control-Request-Headers</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final HeaderName ACCESS_CONTROL_REQUEST_HEADERS = new HeaderName("Access-Control-Request-Headers");
    /**
     * <code>Age</code> message header, see <code>RFC7234</code>.
     */
    public static final HeaderName AGE = new HeaderName("Age");
    /**
     * <code>Allow</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName ALLOW = new HeaderName("Allow");
    /**
     * <code>ALPN</code> message header, see <code>RFC7639</code>.
     */
    public static final HeaderName ALPN = new HeaderName("ALPN");
    /**
     * <code>Alt-Svc</code> message header, see <code>RFC7838</code>.
     */
    public static final HeaderName ALT_SVC = new HeaderName("Alt-Svc");
    /**
     * <code>Alt-Used</code> message header, see <code>RFC7838</code>.
     */
    public static final HeaderName ALT_USED = new HeaderName("Alt-Used");
    /**
     * <code>Alternates</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName ALTERNATES = new HeaderName("Alternates");
    /**
     * <code>AMP-Cache-Transform</code> message header, see <code>https://github.com/ampproject/amphtml/blob/master/spec/amp-cache-transform.md</code>.
     */
    public static final HeaderName AMP_CACHE_TRANSFORM = new HeaderName("AMP-Cache-Transform");
    /**
     * <code>Apply-To-Redirect-Ref</code> message header, see <code>RFC4437</code>.
     */
    public static final HeaderName APPLY_TO_REDIRECT_REF = new HeaderName("Apply-To-Redirect-Ref");
    /**
     * <code>Authentication-Control</code> message header, see <code>RFC8053</code>.
     */
    public static final HeaderName AUTHENTICATION_CONTROL = new HeaderName("Authentication-Control");
    /**
     * <code>Authentication-Info</code> message header, see <code>RFC7615</code>.
     */
    public static final HeaderName AUTHENTICATION_INFO = new HeaderName("Authentication-Info");
    /**
     * <code>Authorization</code> message header, see <code>RFC7235</code>.
     */
    public static final HeaderName AUTHORIZATION = new HeaderName("Authorization");
    /**
     * <code>C-Ext</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName C_EXT = new HeaderName("C-Ext");
    /**
     * <code>C-Man</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName C_MAN = new HeaderName("C-Man");
    /**
     * <code>C-Opt</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName C_OPT = new HeaderName("C-Opt");
    /**
     * <code>C-PEP</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName C_PEP = new HeaderName("C-PEP");
    /**
     * <code>C-PEP-Info</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName C_PEP_INFO = new HeaderName("C-PEP-Info");
    /**
     * <code>Cache-Control</code> message header, see <code>RFC7234</code>.
     */
    public static final HeaderName CACHE_CONTROL = new HeaderName("Cache-Control");
    /**
     * <code>Cal-Managed-ID</code> message header, see <code>RFC8607</code>.
     */
    public static final HeaderName CAL_MANAGED_ID = new HeaderName("Cal-Managed-ID");
    /**
     * <code>CalDAV-Timezones</code> message header, see <code>RFC7809</code>.
     */
    public static final HeaderName CALDAV_TIMEZONES = new HeaderName("CalDAV-Timezones");
    /**
     * <code>CDN-Loop</code> message header, see <code>RFC8586</code>.
     */
    public static final HeaderName CDN_LOOP = new HeaderName("CDN-Loop");
    /**
     * <code>Cert-Not-After</code> message header, see <code>RFC8739</code>.
     */
    public static final HeaderName CERT_NOT_AFTER = new HeaderName("Cert-Not-After");
    /**
     * <code>Cert-Not-Before</code> message header, see <code>RFC8739</code>.
     */
    public static final HeaderName CERT_NOT_BEFORE = new HeaderName("Cert-Not-Before");
    /**
     * <code>Close</code> message header, see <code>RFC7230</code>.
     */
    public static final HeaderName CLOSE = new HeaderName("Close");
    /**
     * <code>Connection</code> message header, see <code>RFC7230</code>.
     */
    public static final HeaderName CONNECTION = new HeaderName("Connection");
    /**
     * <code>Content-Base</code> message header, see <code>RFC2068</code> and <code>RFC2616</code>.
     */
    public static final HeaderName CONTENT_BASE = new HeaderName("Content-Base");
    /**
     * <code>Content-Disposition</code> message header, see <code>RFC6266</code>.
     */
    public static final HeaderName CONTENT_DISPOSITION = new HeaderName("Content-Disposition");
    /**
     * <code>Content-Encoding</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName CONTENT_ENCODING = new HeaderName("Content-Encoding");
    /**
     * <code>Content-ID</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName CONTENT_ID = new HeaderName("Content-ID");
    /**
     * <code>Content-Language</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName CONTENT_LANGUAGE = new HeaderName("Content-Language");
    /**
     * <code>Content-Length</code> message header, see <code>RFC7230</code>.
     */
    public static final HeaderName CONTENT_LENGTH = new HeaderName("Content-Length");
    /**
     * <code>Content-Location</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName CONTENT_LOCATION = new HeaderName("Content-Location");
    /**
     * <code>Content-MD5</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName CONTENT_MD5 = new HeaderName("Content-MD5");
    /**
     * <code>Content-Range</code> message header, see <code>RFC7233</code>.
     */
    public static final HeaderName CONTENT_RANGE = new HeaderName("Content-Range");
    /**
     * <code>Content-Script-Type</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName CONTENT_SCRIPT_TYPE = new HeaderName("Content-Script-Type");
    /**
     * <code>Content-Style-Type</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName CONTENT_STYLE_TYPE = new HeaderName("Content-Style-Type");
    /**
     * <code>Content-Transfer-Encoding</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName CONTENT_TRANSFER_ENCODING = new HeaderName("Content-Transfer-Encoding");
    /**
     * <code>Content-Type</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName CONTENT_TYPE = new HeaderName("Content-Type");
    /**
     * <code>Content-Version</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName CONTENT_VERSION = new HeaderName("Content-Version");
    /**
     * <code>Compliance</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName COMPLIANCE = new HeaderName("Compliance");
    /**
     * <code>Cookie</code> message header, see <code>RFC6265</code>.
     */
    public static final HeaderName COOKIE = new HeaderName("Cookie");
    /**
     * <code>Cookie2</code> message header, see <code>RFC2965</code> and <code>RFC6265</code>.
     */
    public static final HeaderName COOKIE2 = new HeaderName("Cookie2");
    /**
     * <code>Cost</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName COST = new HeaderName("Cost");
    /**
     * <code>DASL</code> message header, see <code>RFC5323</code>.
     */
    public static final HeaderName DASL = new HeaderName("DASL");
    /**
     * <code>DAV</code> message header, see <code>RFC4918</code>.
     */
    public static final HeaderName DAV = new HeaderName("DAV");
    /**
     * <code>Date</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName DATE = new HeaderName("Date");
    /**
     * <code>Default-Style</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName DEFAULT_STYLE = new HeaderName("Default-Style");
    /**
     * <code>Delta-Base</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName DELTA_BASE = new HeaderName("Delta-Base");
    /**
     * <code>Depth</code> message header, see <code>RFC4918</code>.
     */
    public static final HeaderName DEPTH = new HeaderName("Depth");
    /**
     * <code>Derived-From</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName DERIVED_FROM = new HeaderName("Derived-From");
    /**
     * <code>Destination</code> message header, see <code>RFC4918</code>.
     */
    public static final HeaderName DESTINATION = new HeaderName("Destination");
    /**
     * <code>Differential-ID</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName DIFFERENTIAL_ID = new HeaderName("Differential-ID");
    /**
     * <code>Digest</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName DIGEST = new HeaderName("Digest");
    /**
     * <code>Early-Data</code> message header, see <code>RFC8470</code>.
     */
    public static final HeaderName EARLY_DATA = new HeaderName("Early-Data");
    /**
     * <code>EDIINT-Features</code> message header, see <code>RFC6017</code>.
     */
    public static final HeaderName EDIINT_FEATURES = new HeaderName("EDIINT-Features");
    /**
     * <code>ETag</code> message header, see <code>RFC7232</code>.
     */
    public static final HeaderName ETAG = new HeaderName("ETag");
    /**
     * <code>Expect</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName EXPECT = new HeaderName("Expect");
    /**
     * <code>Expect-CT</code> message header, see <code>RFC-ietf-httpbis-expect-ct-08</code>.
     */
    public static final HeaderName EXPECT_CT = new HeaderName("Expect-CT");
    /**
     * <code>Expires</code> message header, see <code>RFC7234</code>.
     */
    public static final HeaderName EXPIRES = new HeaderName("Expires");
    /**
     * <code>Ext</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName EXT = new HeaderName("Ext");
    /**
     * <code>Forwarded</code> message header, see <code>RFC7239</code>.
     */
    public static final HeaderName FORWARDED = new HeaderName("Forwarded");
    /**
     * <code>From</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName FROM = new HeaderName("From");
    /**
     * <code>GetProfile</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName GET_PROFILE = new HeaderName("GetProfile");
    /**
     * <code>Hobareg</code> message header, see <code>RFC7486</code>.
     */
    public static final HeaderName HOBAREG = new HeaderName("Hobareg");
    /**
     * <code>Host</code> message header, see <code>RFC7230</code>.
     */
    public static final HeaderName HOST = new HeaderName("Host");
    /**
     * <code>HTTP2-Settings</code> message header, see <code>RFC7540</code>.
     */
    public static final HeaderName HTTP2_SETTINGS = new HeaderName("HTTP2-Settings");
    /**
     * <code>IM</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName IM = new HeaderName("IM");
    /**
     * <code>If</code> message header, see <code>RFC4918</code>.
     */
    public static final HeaderName IF = new HeaderName("If");
    /**
     * <code>If-Match</code> message header, see <code>RFC7232</code>.
     */
    public static final HeaderName IF_MATCH = new HeaderName("If-Match");
    /**
     * <code>If-Modified-Since</code> message header, see <code>RFC7232</code>.
     */
    public static final HeaderName IF_MODIFIED_SINCE = new HeaderName("If-Modified-Since");
    /**
     * <code>If-None-Match</code> message header, see <code>RFC7232</code>.
     */
    public static final HeaderName IF_NONE_MATCH = new HeaderName("If-None-Match");
    /**
     * <code>If-Range</code> message header, see <code>RFC7233</code>.
     */
    public static final HeaderName IF_RANGE = new HeaderName("If-Range");
    /**
     * <code>If-Schedule-Tag-Match</code> message header, see <code>RFC6638</code>.
     */
    public static final HeaderName IF_SCHEDULE_TAG_MATCH = new HeaderName("If-Schedule-Tag-Match");
    /**
     * <code>If-Unmodified-Since</code> message header, see <code>RFC7232</code>.
     */
    public static final HeaderName IF_UNMODIFIED_SINCE = new HeaderName("If-Unmodified-Since");
    /**
     * <code>Include-Referred-Token-Binding-ID</code> message header, see <code>RFC8473</code>.
     */
    public static final HeaderName INCLUDE_REFERRED_TOKEN_BINDING_ID = new HeaderName("Include-Referred-Token-Binding-ID");
    /**
     * <code>Isolation</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final HeaderName ISOLATION = new HeaderName("Isolation");
    /**
     * <code>Keep-Alive</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName KEEP_ALIVE = new HeaderName("Keep-Alive");
    /**
     * <code>Label</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName LABEL = new HeaderName("Label");
    /**
     * <code>Last-Modified</code> message header, see <code>RFC7232</code>.
     */
    public static final HeaderName LAST_MODIFIED = new HeaderName("Last-Modified");
    /**
     * <code>Link</code> message header, see <code>RFC8288</code>.
     */
    public static final HeaderName LINK = new HeaderName("Link");
    /**
     * <code>Location</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName LOCATION = new HeaderName("Location");
    /**
     * <code>Lock-Token</code> message header, see <code>RFC4918</code>.
     */
    public static final HeaderName LOCK_TOKEN = new HeaderName("Lock-Token");
    /**
     * <code>Man</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName MAN = new HeaderName("Man");
    /**
     * <code>Max-Forwards</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName MAX_FORWARDS = new HeaderName("Max-Forwards");
    /**
     * <code>Memento-Datetime</code> message header, see <code>RFC7089</code>.
     */
    public static final HeaderName MEMENTO_DATETIME = new HeaderName("Memento-Datetime");
    /**
     * <code>Message-ID</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName MESSAGE_ID = new HeaderName("Message-ID");
    /**
     * <code>Meter</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName METER = new HeaderName("Meter");
    /**
     * <code>Method-Check</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final HeaderName METHOD_CHECK = new HeaderName("Method-Check");
    /**
     * <code>Method-Check-Expires</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final HeaderName METHOD_CHECK_EXPIRES = new HeaderName("Method-Check-Expires");
    /**
     * <code>MIME-Version</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName MIME_VERSION = new HeaderName("MIME-Version");
    /**
     * <code>Negotiate</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName NEGOTIATE = new HeaderName("Negotiate");
    /**
     * <code>Non-Compliance</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName NON_COMPLIANCE = new HeaderName("Non-Compliance");
    /**
     * <code>OData-EntityId</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final HeaderName ODATA_ENTITY_ID = new HeaderName("OData-EntityId");
    /**
     * <code>OData-Isolation</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final HeaderName ODATA_ISOLATION = new HeaderName("OData-Isolation");
    /**
     * <code>OData-MaxVersion</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final HeaderName ODATA_MAX_VERSION = new HeaderName("OData-MaxVersion");
    /**
     * <code>OData-Version</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final HeaderName ODATA_VERSION = new HeaderName("OData-Version");
    /**
     * <code>Opt</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName OPT = new HeaderName("Opt");
    /**
     * <code>Optional</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName OPTIONAL = new HeaderName("Optional");
    /**
     * <code>Optional-WWW-Authenticate</code> message header, see <code>RFC8053</code>.
     */
    public static final HeaderName OPTIONAL_WWW_AUTHENTICATE = new HeaderName("Optional-WWW-Authenticate");
    /**
     * <code>Ordering-Type</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName ORDERING_TYPE = new HeaderName("Ordering-Type");
    /**
     * <code>Origin</code> message header, see <code>RFC6454</code>.
     */
    public static final HeaderName ORIGIN = new HeaderName("Origin");
    /**
     * <code>OSCORE</code> message header, see <code>RFC8613</code>.
     */
    public static final HeaderName OSCORE = new HeaderName("OSCORE");
    /**
     * <code>Overwrite</code> message header, see <code>RFC4918</code>.
     */
    public static final HeaderName OVERWRITE = new HeaderName("Overwrite");
    /**
     * <code>P3P</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName P3P = new HeaderName("P3P");
    /**
     * <code>PEP</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName PEP = new HeaderName("PEP");
    /**
     * <code>PICS-Label</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName PICS_LABEL = new HeaderName("PICS-Label");
    /**
     * <code>Pep-Info</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName PEP_INFO = new HeaderName("Pep-Info");
    /**
     * <code>Position</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName POSITION = new HeaderName("Position");
    /**
     * <code>Pragma</code> message header, see <code>RFC7234</code>.
     */
    public static final HeaderName PRAGMA = new HeaderName("Pragma");
    /**
     * <code>Prefer</code> message header, see <code>RFC7240</code>.
     */
    public static final HeaderName PREFER = new HeaderName("Prefer");
    /**
     * <code>Preference-Applied</code> message header, see <code>RFC7240</code>.
     */
    public static final HeaderName PREFERENCE_APPLIED = new HeaderName("Preference-Applied");
    /**
     * <code>ProfileObject</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName PROFILE_OBJECT = new HeaderName("ProfileObject");
    /**
     * <code>Protocol</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName PROTOCOL = new HeaderName("Protocol");
    /**
     * <code>Protocol-Info</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName PROTOCOL_INFO = new HeaderName("Protocol-Info");
    /**
     * <code>Protocol-Query</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName PROTOCOL_QUERY = new HeaderName("Protocol-Query");
    /**
     * <code>Protocol-Request</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName PROTOCOL_REQUEST = new HeaderName("Protocol-Request");
    /**
     * <code>Proxy-Authenticate</code> message header, see <code>RFC7235</code>.
     */
    public static final HeaderName PROXY_AUTHENTICATE = new HeaderName("Proxy-Authenticate");
    /**
     * <code>Proxy-Authentication-Info</code> message header, see <code>RFC7615</code>.
     */
    public static final HeaderName PROXY_AUTHENTICATION_INFO = new HeaderName("Proxy-Authentication-Info");
    /**
     * <code>Proxy-Authorization</code> message header, see <code>RFC7235</code>.
     */
    public static final HeaderName PROXY_AUTHORIZATION = new HeaderName("Proxy-Authorization");
    /**
     * <code>Proxy-Features</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName PROXY_FEATURES = new HeaderName("Proxy-Features");
    /**
     * <code>Proxy-Instruction</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName PROXY_INSTRUCTION = new HeaderName("Proxy-Instruction");
    /**
     * <code>Public</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName PUBLIC = new HeaderName("Public");
    /**
     * <code>Public-Key-Pins</code> message header, see <code>RFC7469</code>.
     */
    public static final HeaderName PUBLIC_KEY_PINS = new HeaderName("Public-Key-Pins");
    /**
     * <code>Public-Key-Pins-Report-Only</code> message header, see <code>RFC7469</code>.
     */
    public static final HeaderName PUBLIC_KEY_PINS_REPORT_ONLY = new HeaderName("Public-Key-Pins-Report-Only");
    /**
     * <code>Range</code> message header, see <code>RFC7233</code>.
     */
    public static final HeaderName RANGE = new HeaderName("Range");
    /**
     * <code>Redirect-Ref</code> message header, see <code>RFC4437</code>.
     */
    public static final HeaderName REDIRECT_REF = new HeaderName("Redirect-Ref");
    /**
     * <code>Referer</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName REFERER = new HeaderName("Referer");
    /**
     * <code>Referer-Root</code> message header, see <code>W3C Web Application Formats Working Group</code>.
     */
    public static final HeaderName REFERER_ROOT = new HeaderName("Referer-Root");
    /**
     * <code>Refresh</code> message header, see <code>https://html.spec.whatwg.org/multipage/browsing-the-web.html#navigating-across-documents%3Ashared-declarative-refresh-steps</code>.
     */
    public static final HeaderName REFRESH = new HeaderName("Refresh");
    /**
     * <code>Replay-Nonce</code> message header, see <code>RFC8555</code>.
     */
    public static final HeaderName REPLAY_NONCE = new HeaderName("Replay-Nonce");
    /**
     * <code>Resolution-Hint</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName RESOLUTION_HINT = new HeaderName("Resolution-Hint");
    /**
     * <code>Resolver-Location</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName RESOLVER_LOCATION = new HeaderName("Resolver-Location");
    /**
     * <code>Retry-After</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName RETRY_AFTER = new HeaderName("Retry-After");
    /**
     * <code>Safe</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName SAFE = new HeaderName("Safe");
    /**
     * <code>Schedule-Reply</code> message header, see <code>RFC6638</code>.
     */
    public static final HeaderName SCHEDULE_REPLY = new HeaderName("Schedule-Reply");
    /**
     * <code>Schedule-Tag</code> message header, see <code>RFC6638</code>.
     */
    public static final HeaderName SCHEDULE_TAG = new HeaderName("Schedule-Tag");
    /**
     * <code>Sec-Token-Binding</code> message header, see <code>RFC8473</code>.
     */
    public static final HeaderName SEC_TOKEN_BINDING = new HeaderName("Sec-Token-Binding");
    /**
     * <code>Sec-WebSocket-Accept</code> message header, see <code>RFC6455</code>.
     */
    public static final HeaderName SEC_WEBSOCKET_ACCEPT = new HeaderName("Sec-WebSocket-Accept");
    /**
     * <code>Sec-WebSocket-Extensions</code> message header, see <code>RFC6455</code>.
     */
    public static final HeaderName SEC_WEBSOCKET_EXTENSIONS = new HeaderName("Sec-WebSocket-Extensions");
    /**
     * <code>Sec-WebSocket-Key</code> message header, see <code>RFC6455</code>.
     */
    public static final HeaderName SEC_WEBSOCKET_KEY = new HeaderName("Sec-WebSocket-Key");
    /**
     * <code>Sec-WebSocket-Protocol</code> message header, see <code>RFC6455</code>.
     */
    public static final HeaderName SEC_WEBSOCKET_PROTOCOL = new HeaderName("Sec-WebSocket-Protocol");
    /**
     * <code>Sec-WebSocket-Version</code> message header, see <code>RFC6455</code>.
     */
    public static final HeaderName SEC_WEBSOCKET_VERSION = new HeaderName("Sec-WebSocket-Version");
    /**
     * <code>Security-Scheme</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName SECURITY_SCHEME = new HeaderName("Security-Scheme");
    /**
     * <code>Server</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName SERVER = new HeaderName("Server");
    /**
     * <code>Set-Cookie</code> message header, see <code>RFC6265</code>.
     */
    public static final HeaderName SET_COOKIE = new HeaderName("Set-Cookie");
    /**
     * <code>Set-Cookie2</code> message header, see <code>RFC2965</code> and <code>RFC6265</code>.
     */
    public static final HeaderName SET_COOKIE2 = new HeaderName("Set-Cookie2");
    /**
     * <code>SetProfile</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName SET_PROFILE = new HeaderName("SetProfile");
    /**
     * <code>SLUG</code> message header, see <code>RFC5023</code>.
     */
    public static final HeaderName SLUG = new HeaderName("SLUG");
    /**
     * <code>SoapAction</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName SOAP_ACTION = new HeaderName("SoapAction");
    /**
     * <code>Status-URI</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName STATUS_URI = new HeaderName("Status-URI");
    /**
     * <code>Strict-Transport-Security</code> message header, see <code>RFC6797</code>.
     */
    public static final HeaderName STRICT_TRANSPORT_SECURITY = new HeaderName("Strict-Transport-Security");
    /**
     * <code>SubOK</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName SUBOK = new HeaderName("SubOK");
    /**
     * <code>Subst</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName SUBST = new HeaderName("Subst");
    /**
     * <code>Sunset</code> message header, see <code>RFC8594</code>.
     */
    public static final HeaderName SUNSET = new HeaderName("Sunset");
    /**
     * <code>Surrogate-Capability</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName SURROGATE_CAPABILITY = new HeaderName("Surrogate-Capability");
    /**
     * <code>Surrogate-Control</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName SURROGATE_CONTROL = new HeaderName("Surrogate-Control");
    /**
     * <code>TCN</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName TCN = new HeaderName("TCN");
    /**
     * <code>TE</code> message header, see <code>RFC7230</code>.
     */
    public static final HeaderName TE = new HeaderName("TE");
    /**
     * <code>Timeout</code> message header, see <code>RFC4918</code>.
     */
    public static final HeaderName TIMEOUT = new HeaderName("Timeout");
    /**
     * <code>Timing-Allow-Origin</code> message header, see <code>https://www.w3.org/TR/resource-timing-1/#timing-allow-origin</code>.
     */
    public static final HeaderName TIMING_ALLOW_ORIGIN = new HeaderName("Timing-Allow-Origin");
    /**
     * <code>Title</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName TITLE = new HeaderName("Title");
    /**
     * <code>Topic</code> message header, see <code>RFC8030</code>.
     */
    public static final HeaderName TOPIC = new HeaderName("Topic");
    /**
     * <code>Traceparent</code> message header, see <code>https://www.w3.org/TR/trace-context/#traceparent-field</code>.
     */
    public static final HeaderName TRACEPARENT = new HeaderName("Traceparent");
    /**
     * <code>Tracestate</code> message header, see <code>https://www.w3.org/TR/trace-context/#tracestate-field</code>.
     */
    public static final HeaderName TRACESTATE = new HeaderName("Tracestate");
    /**
     * <code>Trailer</code> message header, see <code>RFC7230</code>.
     */
    public static final HeaderName TRAILER = new HeaderName("Trailer");
    /**
     * <code>Transfer-Encoding</code> message header, see <code>RFC7230</code>.
     */
    public static final HeaderName TRANSFER_ENCODING = new HeaderName("Transfer-Encoding");
    /**
     * <code>TTL</code> message header, see <code>RFC8030</code>.
     */
    public static final HeaderName TTL = new HeaderName("TTL");
    /**
     * <code>UA-Color</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName UA_COLOR = new HeaderName("UA-Color");
    /**
     * <code>UA-Media</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName UA_MEDIA = new HeaderName("UA-Media");
    /**
     * <code>UA-Pixels</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName UA_PIXELS = new HeaderName("UA-Pixels");
    /**
     * <code>UA-Resolution</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName UA_RESOLUTION = new HeaderName("UA-Resolution");
    /**
     * <code>UA-Windowpixels</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName UA_WINDOWPIXELS = new HeaderName("UA-Windowpixels");
    /**
     * <code>Urgency</code> message header, see <code>RFC8030</code>.
     */
    public static final HeaderName URGENCY = new HeaderName("Urgency");
    /**
     * <code>URI</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName URI = new HeaderName("URI");
    /**
     * <code>Upgrade</code> message header, see <code>RFC7230</code>.
     */
    public static final HeaderName UPGRADE = new HeaderName("Upgrade");
    /**
     * <code>User-Agent</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName USER_AGENT = new HeaderName("User-Agent");
    /**
     * <code>Variant-Vary</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName VARIANT_VARY = new HeaderName("Variant-Vary");
    /**
     * <code>Vary</code> message header, see <code>RFC7231</code>.
     */
    public static final HeaderName VARY = new HeaderName("Vary");
    /**
     * <code>Via</code> message header, see <code>RFC7230</code>.
     */
    public static final HeaderName VIA = new HeaderName("Via");
    /**
     * <code>Version</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName VERSION = new HeaderName("Version");
    /**
     * <code>WWW-Authenticate</code> message header, see <code>RFC7235</code>.
     */
    public static final HeaderName WWW_AUTHENTICATE = new HeaderName("WWW-Authenticate");
    /**
     * <code>Want-Digest</code> message header, see <code>RFC4229</code>.
     */
    public static final HeaderName WANT_DIGEST = new HeaderName("Want-Digest");
    /**
     * <code>Warning</code> message header, see <code>RFC7234</code>.
     */
    public static final HeaderName WARNING = new HeaderName("Warning");
    /**
     * <code>X-Content-Type-Options</code> message header, see <code>RFC7230</code>.
     */
    public static final HeaderName X_CONTENT_TYPE_OPTIONS = new HeaderName("X-Content-Type-Options");
    /**
     * <code>X-Device-Accept</code> message header, see <code>W3C Mobile Web Best Practices Working Group</code>.
     */
    public static final HeaderName X_DEVICE_ACCEPT = new HeaderName("X-Device-Accept");
    /**
     * <code>X-Device-Accept-Charset</code> message header, see <code>W3C Mobile Web Best Practices Working Group</code>.
     */
    public static final HeaderName X_DEVICE_ACCEPT_CHARSET = new HeaderName("X-Device-Accept-Charset");
    /**
     * <code>X-Device-Accept-Encoding</code> message header, see <code>W3C Mobile Web Best Practices Working Group</code>.
     */
    public static final HeaderName X_DEVICE_ACCEPT_ENCODING = new HeaderName("X-Device-Accept-Encoding");
    /**
     * <code>X-Device-Accept-Language</code> message header, see <code>W3C Mobile Web Best Practices Working Group</code>.
     */
    public static final HeaderName X_DEVICE_ACCEPT_LANGUAGE = new HeaderName("X-Device-Accept-Language");
    /**
     * <code>X-Device-User-Agent</code> message header, see <code>W3C Mobile Web Best Practices Working Group</code>.
     */
    public static final HeaderName X_DEVICE_USER_AGENT = new HeaderName("X-Device-User-Agent");
    /**
     * <code>X-Frame-Options</code> message header, see <code>RFC7034</code>.
     */
    public static final HeaderName X_FRAME_OPTIONS = new HeaderName("X-Frame-Options");

    private static final Map<String, HeaderName> KNOWN_HEADERS = new HashMap<>();

    private final String titleCaseName;
    private final String lowerCaseName;

    private HeaderName(final String name) {
        this(name, true);
    }

    private HeaderName(final String name, final boolean register) {
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

    @Override
    public String toString() {
        return titleCaseName;
    }

    /**
     * Creates specified message header.
     * @param name header name
     * @return message header instance
     * @throws IllegalArgumentException if message header name doesn't match HTTP's spec. <code>token</code> definition
     */
    public static HeaderName of(final String name) {
        validateToken(name);
        HeaderName retVal = KNOWN_HEADERS.get(name.toLowerCase(Locale.US));
        return retVal != null ? retVal : new HeaderName(name, false);
    }

}
