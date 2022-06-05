---
author: Chris Kipp - BE Scala 
date: MM-dd-YY
paging: "%d/%d"
---

# Interrelated protocols in the editing experience
## LSP, BSP, DAP, how's it all work together?


```
     ┌──────┐          ┌──────┐          ┌────────────┐
     │Editor│          │Metals│          │Build Server│
     └──┬───┘          └──┬───┘          └─────┬──────┘
        │       LSP       │                    │       
        │ <─────────────> │                    │       
        │                 │                    │       
        │                 │         BSP        │       
        │                 │ <────────────────> │     
        │                 │                    │       
        │                 │DAP                 │       
        │ <──────────────────────────────────> │     
     ┌──┴───┐          ┌──┴───┐          ┌─────┴──────┐
     │Editor│          │Metals│          │Build Server│
     └──────┘          └──────┘          └────────────┘
```


---

# Hi, I'm Chris 👋

  - Software developer at **Lunatech**
  - I enjoy working in open-source Scala tooling
      - Metals (maintainer)
      - Scoverage (maintainer)
      - `nvim-metals` (author)
      - plus some other small things...
  - Host a pocast called [Tooling Talks](tooling-talks.com)
  - A big fan of Neovim

---

# IDEs (_Integrated Development Environment_)

_Everything all packaged together_
```
,---------------------IDE-----------------------.
|  ,-----------------.  ,-------------------.   |
|  |text manipulation|  |syntax highlighting|   |
|  |                 |  |                   |   |
|  `-----------------'  `-------------------'   |
|                                               |
|                                               |
|  ,----------------.   ,-----------------.     |
|  |language support|   |run/debug support|     |
|  |                |   |                 |     |
|  `----------------'   `-----------------'     |
|                                               |
`-----------------------------------------------'
```
## Examples
   - IntelliJ
   - Eclipse

---

# A protocol driven approach 

```
     ┌──────┐          ┌───────────────┐          ┌────────────┐
     │Editor│          │Language Server│          │Build Server│
     └──┬───┘          └───────┬───────┘          └─────┬──────┘
        │         LSP          │                        │       
        │ <───────────────────>│                        │       
        │                      │                        │       
        │                      │          BSP           │       
        │                      │<──────────────────────>│       
        │                      │                        │       
        │                      DAP                      │      
        │ <───────────────────────────────────────────> │    
     ┌──┴───┐          ┌───────┴───────┐          ┌─────┴──────┐
     │Editor│          │Language Server│          │Build Server│
     └──────┘          └───────────────┘          └────────────┘
```

## Examples
   - The Metals ecosystem

---

# Language Server Protocol

```
     ┌──────┐                   ┌───────────────┐
     │Editor│                   │Language Server│
     └──┬───┘                   └───────┬───────┘
        │ LSP (Language Server Protocol)│        
        │ <───────────────────────────> │       
     ┌──┴───┐                   ┌───────┴───────┐
     │Editor│                   │Language Server│
     └──────┘                   └───────────────┘
```

## Example

```
     ┌───────┐          ┌──────┐
     │VS Code│          │Metals│
     └───┬───┘          └──┬───┘
         │                 │    
         │<───────────────>│    
     ┌───┴───┐          ┌──┴───┐
     │VS Code│          │Metals│
     └───────┘          └──────┘
```


---
# Build Server Protocol

```
     ┌───────────────┐             ┌────────────┐
     │Language Server│             │Build Server│
     └───────┬───────┘             └─────┬──────┘
             │BSP (Build Server Protocol)│       
             │ <───────────────────────> │      
     ┌───────┴───────┐             ┌─────┴──────┐
     │Language Server│             │Build Server│
     └───────────────┘             └────────────┘
```
## Example

```
     ┌──────┐          ┌─────┐
     │Metals│          │Bloop│
     └──┬───┘          └──┬──┘
        │                 │   
        │ <──────────────>│   
     ┌──┴───┐          ┌──┴──┐
     │Metals│          │Bloop│
     └──────┘          └─────┘
```

---

# Debug Adapter Protocol

_You'd assume_
```
     ┌──────────┐          ┌──────────┐
     │DAP Client│          │Dap Server│
     └────┬─────┘          └────┬─────┘
          │                     │      
          │ <─────────────────> │    
     ┌────┴─────┐          ┌────┴─────┐
     │DAP Client│          │Dap Server│
     └──────────┘          └──────────┘
```

---

# Debug Adapter Protocol

_But in reality it's sort of more like_

```
     ┌────────────┐          ┌─────────────┐          ┌────────┐
     │Debug Client│          │Debug Adapter│          │Debugger│
     └─────┬──────┘          └──────┬──────┘          └───┬────┘
           │                        │                     │     
           │ <─────────────────────>│                     │     
           │                        │                     │     
           │                        │                     │     
           │                        │<───────────────────>│     
     ┌─────┴──────┐          ┌──────┴──────┐          ┌───┴────┐
     │Debug Client│          │Debug Adapter│          │Debugger│
     └────────────┘          └─────────────┘          └────────┘
```

---

# Debug Adapter Protocol

_And yet even more tricky when you look at Metals_

```
     ┌───────────┐          ┌────────┐          ┌──────┐          ┌────────────┐          ┌─────────────┐          ┌────────┐          
     │nvim-metals│          │nvim-dap│          │metals│          │build server│          │debug adapter│          │debugger│          
     └─────┬─────┘          └───┬────┘          └──┬───┘          └─────┬──────┘          └──────┬──────┘          └───┬────┘          
           │                    │                  │                    │                        │                     │               
           │<──────────────────>│                  │                    │                        │                     │               
           │                    │                  │                    │                        │                     │               
           │                    │                  │                    │                        │                     │               
           │<─────────────────────────────────────>│                    │                        │                     │               
           │                    │                  │                    │                        │                     │               
           │                    │                  │                    │                        │                     │               
           │                    │ <────────────────>                    │                        │                     │               
           │                    │                  │                    │                        │                     │               
           │                    │                  │                    │                        │                     │               
           │                    │     ╔════════════╪════════════════════╪═══════╤════════════════╪═════════════════════╪══════════════╗
           │                    │     ║ "MUCH OF THIS IS ALL WRAPPED TOGETHER"  │                │                     │              ║
           │                    │     ╟─────────────────────────────────────────┘                │                     │              ║
           │                    │     ║            │ <──────────────────>                        │                     │              ║
           │                    │     ║            │                    │                        │                     │              ║
           │                    │     ║            │                    │                        │                     │              ║
           │                    │     ║            │ <──────────────────────────────────────────>│                     │              ║
           │                    │     ║            │                    │                        │                     │              ║
           │                    │     ║            │                    │                        │                     │              ║
           │                    │     ║            │                    │                        │<───────────────────>│              ║
           │                    │     ╚════════════╪════════════════════╪════════════════════════╪═════════════════════╪══════════════╝
     ┌─────┴─────┐          ┌───┴────┐          ┌──┴───┐          ┌─────┴──────┐          ┌──────┴──────┐          ┌───┴────┐          
     │nvim-metals│          │nvim-dap│          │metals│          │build server│          │debug adapter│          │debugger│          
     └───────────┘          └────────┘          └──────┘          └────────────┘          └─────────────┘          └────────┘          
```

---

# You might be saying or asking...

> "Chris, why is there so many moving parts? This is so confusing"

Maybe, but *most* of it is abstracted away from the user

> "I'm amazed this even works at all"

Me too

> "Can you give an actual example to show all this"

Let's dive in
