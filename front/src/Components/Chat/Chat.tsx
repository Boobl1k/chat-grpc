import React, {useState, useEffect} from 'react';
import ChatWindow from "./ChatWindow/ChatWindow";
import ChatInput from "./ChatInput/ChatInput";
import IMessage from "../../entities/IMessage";
import {chatClient} from '../../clients';
import useAuthMeta from '../../hooks/authMeta';

export default function Chat() {
    const [chat, setChat] = useState<IMessage[]>([]);
    const authMeta = useAuthMeta();

    useEffect(() => {
        const stream = chatClient.getMessages({}, {meta: authMeta})
        stream.responses.onNext(response => {
            if (response)
                setChat(prev => {
                    const newMessage: IMessage = {
                        id: response.id,
                        username: response.authorUsername,
                        text: response.text
                    };
                    return [...prev, newMessage];
                })
        })
    }, [])

    const sendMessage = async (text: string) => {
        chatClient.sendMessage({text}, {meta: authMeta});
    }

    return (
        <div className="flex flex-col flex-grow w-full max-w-xl bg-white shadow-xl rounded-lg overflow-hidden">
            <ChatWindow chat={chat}/>
            <hr/>
            <ChatInput sendMessage={sendMessage}/>
        </div>
    );
}