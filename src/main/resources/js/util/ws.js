import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';


let stompClient = null;
const handlers = [];

export function connect() 
{
    // let client = Stomp.over(function()
    // {
    //     return new WebSocket('ws://localhost:15674/ws')
    // });

    const socket = new SockJS('/ws');
    stompClient = Stomp.over(() => socket);
    stompClient.debug = () => {};

    stompClient.connect({}, frame => 
    {
        // console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/activity', data => 
        {
            handlers.forEach(handler => handler(JSON.parse(data.body)));
        });
    });
}

export function addHandler(handler) 
{
    handlers.push(handler);
}

export function disconnect() 
{
    if (stompClient !== null) stompClient.disconnect();
    // console.log("Disconnected");
}

export function sendPost(post) 
{
    stompClient.send("/app/change_post", {}, JSON.stringify(post));
}