package com.miguel.proyecto_android

object ClientManager {

    private val clients = mutableListOf<Client>(
        Client(
            "Cliente 1",
            "Client1@gmail.com",
            111111111,
            "https://picsum.photos/300?random=1"
        ),Client(
            "Cliente 2",
            "Client2@gmail.com",
            222222222,
            "https://picsum.photos/300?random=2"
        ),Client(
            "Cliente 3",
            "Client3@gmail.com",
            333333333,
            "https://picsum.photos/300?random=3"
        ),Client(
            "Cliente 4",
            "Client4@gmail.com",
            444444444,
            "https://picsum.photos/300?random=4"
        ),Client(
            "Cliente 5",
            "Client5@gmail.com",
            555555555,
            "https://picsum.photos/300?random=5"
        ),Client(
            "Cliente 6",
            "Client6@gmail.com",
            666666666,
            "https://picsum.photos/300?random=6"
        ),
    )



    fun addClient(client: Client){
        clients.add(client)
    }
    fun deleteCLient(position: Int){
        clients.removeAt(position)
    }
    fun modifyCLient(position: Int, client: Client){
        clients[position] = client
    }
    fun findClient(position: Int):Client{
        return clients[position]
    }
    fun getClients():List<Client>{
        return clients
    }

}