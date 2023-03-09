import React, {useRef, useState} from 'react';

interface ChatInputProps {
    sendMessage: (message: string) => void;
}

export default function ChatInput({sendMessage}: ChatInputProps) {
    const message = useRef<HTMLInputElement>(null);

    const submitHandler = (event: React.FormEvent) => {
        event.preventDefault();

        if (message.current?.value) {
            sendMessage(message.current.value);
        }
    }

    return (
        <form onSubmit={submitHandler} className="bg-gray-300 p-1">
            <input
                type="text"
                id="message"
                name="message"
                ref={message}
                className="flex items-center h-7 w-full rounded px-2 text-sm"
                placeholder="message"
            />
            <input type="Submit" value="Submit" className="invisible"></input>
        </form>
    )
}
