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
