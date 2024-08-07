// elements
const setComp           = document.getElementById('setComp')
const msgComp           = document.getElementById('msgComp')
const portSendBtn       = document.getElementById('port_send_btn')
const portNm            = document.getElementById('portNm')
const roomNm            = document.getElementById('roomNm')
const userNm            = document.getElementById('userNm')
const setNm             = document.getElementById('setNm')
const setRoomNm         = document.getElementById('setRoomNm')
const setMsg            = document.getElementById('setMsg')
const msg_send_btn      = document.getElementById('msg_send')
const chatroom_body     = document.getElementById('chatroom_body')
const chatroom_comp     = document.getElementById('chatroom_comp')
const chatroom_btn      = document.getElementById('chatroom_exit')


let   socket      = ''
let   stompClient = ''


portSendBtn.addEventListener('click',()=>{
    if( portNm === '' ){
        alert("You must have to set app port number")
    }
    else{
               
        let url = 'http://localhost:'+portNm.value+'/app';
        try {
            socket            = new SockJS(url);
            stompClient       = Stomp.over(socket);
            stompClient.connect({}, (frame)=>{

                      msgComp.classList.remove('close') // message 설정 componet open
                chatroom_comp.classList.remove('close') // chatting component open
                      setComp.classList.add('close')    // chatting 연결 component close
                let name            = userNm.value
                let room            = roomNm.value 
                setNm.value         = name
                setRoomNm.value     = room
                setNm.innerHTML     = "USER NAME : "+name
                setRoomNm.innerHTML = "ROOM NUM  : "+room
                subRoom(room)    
                
                if( frame.command === 'CONNECT' ){
                    console.log("frame.command : ",frame.command)
                }
                
            },(error)=>{
                console.error("Connection Error : ",error)
            })

        } catch (error) {
            console.log(error)
        }

    }
})

function showMsg(msg){

    // convert to json
    let jsonMsg  = JSON.parse(msg)

    // new div 
    let chatDiv  = document.createElement('div')

    // new msg div
    let msgDiv   = document.createElement('div')

    // new nick div 
    let nickDiv  = document.createElement('div')
        nickDiv.className   = 'nick'
        nickDiv.textContent = jsonMsg.name
    
    let previousMessageNick = getPreviousNick() 

    if( previousMessageNick != null  && previousMessageNick === jsonMsg.name){
        nickDiv.style.display = 'none'
    }

    
    if( jsonMsg.name === setNm.value ){
        chatDiv.className   = 'my_chat'
         msgDiv.className   = 'my_message'
    }
    else{
        chatDiv.className   = 'user_chat'
         msgDiv.className   = 'user_message'
    }

    msgDiv.innerHTML = jsonMsg.message

    chatDiv.appendChild(nickDiv)
    chatDiv.appendChild(msgDiv)

    chatroom_body.appendChild(chatDiv)    

    // scrolling 
    chatroom_body.scrollTop = chatroom_body.scrollHeight
}


function subRoom(roomNm){
    stompClient.subscribe('/topic/room/'+roomNm,(message)=>{
        let msg = message.body.trim()
        showMsg(msg)
    })
}

function sendMsg(){
    const userRoomNum = setRoomNm.value
    const userNm      = setNm.value
    const userMsg     = setMsg.value

    if( userMsg === '' ){
        alert('Please insert something!')
    }
    else{

        if( stompClient && stompClient.connected ) {
            stompClient.send('/pub/send',{},JSON.stringify({
                name:userNm,
                room:userRoomNum,
                message:userMsg
            }))   
            setMsg.value = ''
            setMsg.innerHTML = ''
        }
        else{
            console.log('setting not yet')
        }        
    }
}

// sending message click
msg_send_btn.addEventListener('click',()=>{
    console.log('message sending operation')
    sendMsg()
})

// sending message keydown
setMsg.addEventListener('keydown',(event)=>{
    if(event.key === 'Enter'){
        event.preventDefault();
        sendMsg()
    }
})



// chatting room exit 
chatroom_btn.addEventListener('click',()=>{

    //chatting reset
    chatroom_body.innerHTML = ''

    chatroom_comp.classList.add('close')
          msgComp.classList.add('close') // message 설정 componet open
          setComp.classList.remove('close')  

    stompClient.disconnect(()=>{
        console.log("WS Connection Terminated")
    })
})


function getPreviousNick(){
    let childrenLen            = chatroom_body.children.length
    console.log('childrenLen : ',childrenLen)
    if( childrenLen > 0 ){
        let temp = chatroom_body.children[childrenLen-1];
        console.log('childrenLen : '+childrenLen +" temp.querySelector('.nick').textContent : "+temp.querySelector('.nick').textContent)
        return temp.querySelector('.nick').textContent
    }
    return null
}