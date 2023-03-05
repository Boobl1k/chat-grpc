import React, {useState, useEffect} from 'react';
import ChatWindow from "./ChatWindow/ChatWindow";
import ChatInput from "./ChatInput/ChatInput";
import IMessage from "../entities/IMessage";
import {API_URL} from '../config';
import * as grpcWeb from 'grpc-web';
import {ChatClient} from '../proto/generated/chat.client';
import {AuthClient} from '../proto/generated/auth.client';
import {GrpcWebFetchTransport} from '@protobuf-ts/grpcweb-transport'
import {SendMessageRequest} from '../proto/generated/chat';

export default function Chat() {
    const [chat, setChat] = useState<IMessage[]>([]);
    const transport = new GrpcWebFetchTransport({
        baseUrl: API_URL,
        timeout: 1000000000000000
    })
    const chatClient = new ChatClient(transport);
    const authClient = new AuthClient(transport);
    const [token, setToken] = useState<string>();

    useEffect(() => {
        const result = authClient.login({
            username: "test_username",
            password: "test_password"
        }).then(t => {
            console.log(t.response.token);
            setToken(t.response.token);
        })
    }, [])

    useEffect(() => {
        if (token) {
            const stream = chatClient.getMessages({}, {meta: {'Authorization': `Bearer ${token}`}, timeout: 10000000000000})
            stream.responses.onNext(response => {
                if (response)
                    setChat(prev => {
                        const newMessage: IMessage = {
                            username: response.authorUsername,
                            text: response.text
                        };
                        return [...prev, newMessage];
                    })
            })
        }
    }, [token])

    const sendMessage = async (userName: string, text: string) => {
        chatClient.sendMessage({text}, {meta: {'Authorization': `Bearer ${token}`}});
    }


    return (
        <div className="flex flex-col flex-grow w-full max-w-xl bg-white shadow-xl rounded-lg overflow-hidden">
            <ChatWindow chat={chat}/>
            <hr/>
            <ChatInput sendMessage={sendMessage}/>
        </div>
    );
}