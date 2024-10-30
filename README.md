Leonardo Luz & Diego Prestes <br/>
4º Semestre ADS <br/>
[Repositório](https://github.com/leonardo-luz/social-media-app-college-project)

---
## Sumário	
1. [Descrição](#Descrição)
2. [Objetivos](#Objetivos)
	1. [Primários](#Primários)
	2. [Secundários](#Secundários)
3. [Tecnologias](#Tecnologias)
	1. [Frontend](#Frontend)
	2. [Backend](#Backend)
4. [Model banco de dados](#ModeloBancodedados)
	1. [Relacional](#Relacional)
	2. [Lógico](#Lógico)
5. [Prototipação](#Prototipação)
6. [Considerações Finais](#ConsideraçõesFinais)

---
### Descrição
>Este projeto terá como objetivo principal desenvolver uma Rede social focada em interação em tempo real entre usuários apartir de um chat global, existindo também a opção de conversar em particular com amigos adicionados.

---
### Objetivos

##### - Primários:
- [ ] Chat global
- [ ] Autenticação de usuário
- [ ] Lista de usuário online
- [ ] Perfil do usuário

##### - Secundários:
- [ ] Adicionar amigos
- [ ] Lista de amigos online e offline
- [ ] Chat privado 
- [ ] Grupo privado
- [ ] Grupo publico
- [ ] Adicionar foto ao perfil
- [ ] Bloquear usuário
- [ ] Enviar images por mensagem

---

### Tecnologias

- Java JDK 21

#### - Frontend:
- JavaFx 21
	- Para a implementação de interface gráfica.

#### - Backend:

---
### Modelo Banco de Dados

```
User(userId, name, username, password, createdAt, updatedAt)
```

```
User_Friend(userId, friendId, createdAt, updatedAt)
	userId Referencia User
	friendId Referencia User
```

```
User_Block(userId, blockId, createdAt, updatedAt)
	userId Referencia User
	blockId Referencia User
```

```
Chat(chatId, title, adminId, createdAt, updatedAt)
	adminId Referencia User
```

```
Chat_User(Chat_User_Id, chatId, userId, createdAt, updatedAt)
	chatId Referencia Chat
	userId Referencia User
```

```
Message(messageId, text, userId, chatId, createdAt, updatedAt)
	userId Referencia User
	chatId Referencia Chat
```

#### Relacional

![er_model](./prototipation/ER.png)

#### Lógico
![logic_model](./prototipation/Lógico.png)

---
### Prototipação [WIP]

![main_prototipe](./prototipation/main.png)

---

### Considerações Finais
> Dado a extensão do projeto, provavelmente não será possivel implementar todas as funcionabilidades secundárias previstas até o prazo final.