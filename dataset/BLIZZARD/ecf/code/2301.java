/*******************************************************************************
* Copyright (c) 2009 IBM, and others. 
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*   IBM Corporation - initial API and implementation
******************************************************************************/
package org.eclipse.ecf.provider.filetransfer.events.socket;

import java.net.Socket;
import org.eclipse.ecf.filetransfer.events.socket.ISocketCreatedEvent;
import org.eclipse.ecf.filetransfer.events.socket.ISocketEventSource;

public class SocketCreatedEvent extends AbstractSocketEvent implements ISocketCreatedEvent {

    public  SocketCreatedEvent(ISocketEventSource source, Socket socket) {
        super(source, socket, socket);
    }

    protected String getEventName() {
        //$NON-NLS-1$
        return "SocketCreatedEvent";
    }
}
