import { GrpcWebFetchTransport } from "@protobuf-ts/grpcweb-transport";
import { API_URL } from "./config";
import {AuthClient} from "./proto/generated/auth.client";
import {ChatClient} from "./proto/generated/chat.client";

export const transport = new GrpcWebFetchTransport({baseUrl: API_URL});

export const authClient = new AuthClient(transport);
export const chatClient = new ChatClient(transport);
