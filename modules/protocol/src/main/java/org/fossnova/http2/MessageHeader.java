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
    /**
     * <code>IM</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader IM = new MessageHeader("IM");
    /**
     * <code>If</code> message header, see <code>RFC4918</code>.
     */
    public static final MessageHeader IF = new MessageHeader("If");
    /**
     * <code>If-Match</code> message header, see <code>RFC7232</code>.
     */
    public static final MessageHeader IF_MATCH = new MessageHeader("If-Match");
    /**
     * <code>If-Modified-Since</code> message header, see <code>RFC7232</code>.
     */
    public static final MessageHeader IF_MODIFIED_SINCE = new MessageHeader("If-Modified-Since");
    /**
     * <code>If-None-Match</code> message header, see <code>RFC7232</code>.
     */
    public static final MessageHeader IF_NONE_MATCH = new MessageHeader("If-None-Match");
    /**
     * <code>If-Range</code> message header, see <code>RFC7233</code>.
     */
    public static final MessageHeader IF_RANGE = new MessageHeader("If-Range");
    /**
     * <code>If-Schedule-Tag-Match</code> message header, see <code>RFC6638</code>.
     */
    public static final MessageHeader IF_SCHEDULE_TAG_MATCH = new MessageHeader("If-Schedule-Tag-Match");
    /**
     * <code>If-Unmodified-Since</code> message header, see <code>RFC7232</code>.
     */
    public static final MessageHeader IF_UNMODIFIED_SINCE = new MessageHeader("If-Unmodified-Since");
    /**
     * <code>Include-Referred-Token-Binding-ID</code> message header, see <code>RFC8473</code>.
     */
    public static final MessageHeader INCLUDE_REFERRED_TOKEN_BINDING_ID = new MessageHeader("Include-Referred-Token-Binding-ID");
    /**
     * <code>Keep-Alive</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader KEEP_ALIVE = new MessageHeader("Keep-Alive");
    /**
     * <code>Label</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader LABEL = new MessageHeader("Label");
    /**
     * <code>Last-Modified</code> message header, see <code>RFC7232</code>.
     */
    public static final MessageHeader LAST_MODIFIED = new MessageHeader("Last-Modified");
    /**
     * <code>Link</code> message header, see <code>RFC8288</code>.
     */
    public static final MessageHeader LINK = new MessageHeader("Link");
    /**
     * <code>Location</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader LOCATION = new MessageHeader("Location");
    /**
     * <code>Lock-Token</code> message header, see <code>RFC4918</code>.
     */
    public static final MessageHeader LOCK_TOKEN = new MessageHeader("Lock-Token");
    /**
     * <code>Man</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader MAN = new MessageHeader("Man");
    /**
     * <code>Max-Forwards</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader MAX_FORWARDS = new MessageHeader("Max-Forwards");
    /**
     * <code>Memento-Datetime</code> message header, see <code>RFC7089</code>.
     */
    public static final MessageHeader MEMENTO_DATETIME = new MessageHeader("Memento-Datetime");
    /**
     * <code>Meter</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader METER = new MessageHeader("Meter");
    /**
     * <code>MIME-Version</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader MIME_VERSION = new MessageHeader("MIME-Version");
    /**
     * <code>Negotiate</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader NEGOTIATE = new MessageHeader("Negotiate");
    /**
     * <code>OData-EntityId</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final MessageHeader ODATA_ENTITY_ID = new MessageHeader("OData-EntityId");
    /**
     * <code>OData-Isolation</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final MessageHeader ODATA_ISOLATION = new MessageHeader("OData-Isolation");
    /**
     * <code>OData-MaxVersion</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final MessageHeader ODATA_MAX_VERSION = new MessageHeader("OData-MaxVersion");
    /**
     * <code>OData-Version</code> message header, see <code>OData Version 4.01</code>.
     */
    public static final MessageHeader ODATA_VERSION = new MessageHeader("OData-Version");
    /**
     * <code>Opt</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader OPT = new MessageHeader("Opt");
    /**
     * <code>Optional-WWW-Authenticate</code> message header, see <code>RFC8053</code>.
     */
    public static final MessageHeader OPTIONAL_WWW_AUTHENTICATE = new MessageHeader("Optional-WWW-Authenticate");
    /**
     * <code>Ordering-Type</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader ORDERING_TYPE = new MessageHeader("Ordering-Type");
    /**
     * <code>Origin</code> message header, see <code>RFC6454</code>.
     */
    public static final MessageHeader ORIGIN = new MessageHeader("Origin");
    /**
     * <code>OSCORE</code> message header, see <code>RFC8613</code>.
     */
    public static final MessageHeader OSCORE = new MessageHeader("OSCORE");
    /**
     * <code>Overwrite</code> message header, see <code>RFC4918</code>.
     */
    public static final MessageHeader OVERWRITE = new MessageHeader("Overwrite");
    /**
     * <code>P3P</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader P3P = new MessageHeader("P3P");
    /**
     * <code>PEP</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader PEP = new MessageHeader("PEP");
    /**
     * <code>PICS-Label</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader PICS_LABEL = new MessageHeader("PICS-Label");
    /**
     * <code>Pep-Info</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader PEP_INFO = new MessageHeader("Pep-Info");
    /**
     * <code>Position</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader POSITION = new MessageHeader("Position");
    /**
     * <code>Pragma</code> message header, see <code>RFC7234</code>.
     */
    public static final MessageHeader PRAGMA = new MessageHeader("Pragma");
    /**
     * <code>Prefer</code> message header, see <code>RFC7240</code>.
     */
    public static final MessageHeader PREFER = new MessageHeader("Prefer");
    /**
     * <code>Preference-Applied</code> message header, see <code>RFC7240</code>.
     */
    public static final MessageHeader PREFERENCE_APPLIED = new MessageHeader("Preference-Applied");
    /**
     * <code>ProfileObject</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader PROFILE_OBJECT = new MessageHeader("ProfileObject");
    /**
     * <code>Protocol</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader PROTOCOL = new MessageHeader("Protocol");
    /**
     * <code>Protocol-Info</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader PROTOCOL_INFO = new MessageHeader("Protocol-Info");
    /**
     * <code>Protocol-Query</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader PROTOCOL_QUERY = new MessageHeader("Protocol-Query");
    /**
     * <code>Protocol-Request</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader PROTOCOL_REQUEST = new MessageHeader("Protocol-Request");
    /**
     * <code>Proxy-Authenticate</code> message header, see <code>RFC7235</code>.
     */
    public static final MessageHeader PROXY_AUTHENTICATE = new MessageHeader("Proxy-Authenticate");
    /**
     * <code>Proxy-Authentication-Info</code> message header, see <code>RFC7615</code>.
     */
    public static final MessageHeader PROXY_AUTHENTICATION_INFO = new MessageHeader("Proxy-Authentication-Info");
    /**
     * <code>Proxy-Authorization</code> message header, see <code>RFC7235</code>.
     */
    public static final MessageHeader PROXY_AUTHORIZATION = new MessageHeader("Proxy-Authorization");
    /**
     * <code>Proxy-Features</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader PROXY_FEATURES = new MessageHeader("Proxy-Features");
    /**
     * <code>Proxy-Instruction</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader PROXY_INSTRUCTION = new MessageHeader("Proxy-Instruction");
    /**
     * <code>Public</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader PUBLIC = new MessageHeader("Public");
    /**
     * <code>Public-Key-Pins</code> message header, see <code>RFC7469</code>.
     */
    public static final MessageHeader PUBLIC_KEY_PINS = new MessageHeader("Public-Key-Pins");
    /**
     * <code>Public-Key-Pins-Report-Only</code> message header, see <code>RFC7469</code>.
     */
    public static final MessageHeader PUBLIC_KEY_PINS_REPORT_ONLY = new MessageHeader("Public-Key-Pins-Report-Only");
    /**
     * <code>Range</code> message header, see <code>RFC7233</code>.
     */
    public static final MessageHeader RANGE = new MessageHeader("Range");
    /**
     * <code>Redirect-Ref</code> message header, see <code>RFC4437</code>.
     */
    public static final MessageHeader REDIRECT_REF = new MessageHeader("Redirect-Ref");
    /**
     * <code>Referer</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader REFERER = new MessageHeader("Referer");
    /**
     * <code>Replay-Nonce</code> message header, see <code>RFC8555</code>.
     */
    public static final MessageHeader REPLAY_NONCE = new MessageHeader("Replay-Nonce");
    /**
     * <code>Retry-After</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader RETRY_AFTER = new MessageHeader("Retry-After");
    /**
     * <code>Safe</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader SAFE = new MessageHeader("Safe");
    /**
     * <code>Schedule-Reply</code> message header, see <code>RFC6638</code>.
     */
    public static final MessageHeader SCHEDULE_REPLY = new MessageHeader("Schedule-Reply");
    /**
     * <code>Schedule-Tag</code> message header, see <code>RFC6638</code>.
     */
    public static final MessageHeader SCHEDULE_TAG = new MessageHeader("Schedule-Tag");
    /**
     * <code>Sec-Token-Binding</code> message header, see <code>RFC8473</code>.
     */
    public static final MessageHeader SEC_TOKEN_BINDING = new MessageHeader("Sec-Token-Binding");
    /**
     * <code>Sec-WebSocket-Accept</code> message header, see <code>RFC6455</code>.
     */
    public static final MessageHeader SEC_WEBSOCKET_ACCEPT = new MessageHeader("Sec-WebSocket-Accept");
    /**
     * <code>Sec-WebSocket-Extensions</code> message header, see <code>RFC6455</code>.
     */
    public static final MessageHeader SEC_WEBSOCKET_EXTENSIONS = new MessageHeader("Sec-WebSocket-Extensions");
    /**
     * <code>Sec-WebSocket-Key</code> message header, see <code>RFC6455</code>.
     */
    public static final MessageHeader SEC_WEBSOCKET_KEY = new MessageHeader("Sec-WebSocket-Key");
    /**
     * <code>Sec-WebSocket-Protocol</code> message header, see <code>RFC6455</code>.
     */
    public static final MessageHeader SEC_WEBSOCKET_PROTOCOL = new MessageHeader("Sec-WebSocket-Protocol");
    /**
     * <code>Sec-WebSocket-Version</code> message header, see <code>RFC6455</code>.
     */
    public static final MessageHeader SEC_WEBSOCKET_VERSION = new MessageHeader("Sec-WebSocket-Version");
    /**
     * <code>Security-Scheme</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader SECURITY_SCHEME = new MessageHeader("Security-Scheme");
    /**
     * <code>Server</code> message header, see <code>RFC7231</code>.
     */
    public static final MessageHeader SERVER = new MessageHeader("Server");
    /**
     * <code>Set-Cookie</code> message header, see <code>RFC6265</code>.
     */
    public static final MessageHeader SET_COOKIE = new MessageHeader("Set-Cookie");
    /**
     * <code>Set-Cookie2</code> message header, see <code>RFC2965</code> and <code>RFC6265</code>.
     */
    public static final MessageHeader SET_COOKIE2 = new MessageHeader("Set-Cookie2");
    /**
     * <code>SetProfile</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader SET_PROFILE = new MessageHeader("SetProfile");
    /**
     * <code>SLUG</code> message header, see <code>RFC5023</code>.
     */
    public static final MessageHeader SLUG = new MessageHeader("SLUG");
    /**
     * <code>SoapAction</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader SOAP_ACTION = new MessageHeader("SoapAction");
    /**
     * <code>Status-URI</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader STATUS_URI = new MessageHeader("Status-URI");
    /**
     * <code>Strict-Transport-Security</code> message header, see <code>RFC6797</code>.
     */
    public static final MessageHeader STRICT_TRANSPORT_SECURITY = new MessageHeader("Strict-Transport-Security");
    /**
     * <code>Sunset</code> message header, see <code>RFC8594</code>.
     */
    public static final MessageHeader SUNSET = new MessageHeader("Sunset");
    /**
     * <code>Surrogate-Capability</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader SURROGATE_CAPABILITY = new MessageHeader("Surrogate-Capability");
    /**
     * <code>Surrogate-Control</code> message header, see <code>RFC4229</code>.
     */
    public static final MessageHeader SURROGATE_CONTROL = new MessageHeader("Surrogate-Control");

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
