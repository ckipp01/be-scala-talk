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
      - **Metals** (maintainer)
      - **Scoverage** (maintainer)
      - `nvim-metals` (author)
      - plus some other small things...
   - Host a podcast called [**Tooling Talks**](tooling-talks.com)
   - A big fan of **Neovim**
   - For better or worse, I also enjoy hacking in Lua

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

---

# Some questions you might have

> How does it even know what the main method is?

> How does it know to debug or just run?

> How does your debug client know where your debug server is?

> What does the DAP communication actually look like?

> What if I think code lenses are dumb and don't want to use them?

---

# How does it even know what the main method is?
## BSP - `buildTarget/scalaMainClasses`


```json

[Trace - 00:50:15 PM] Sending request 'buildTarget/scalaMainClasses - (13)'
Params: {
  "targets": [
    {
      "uri": "file:/Users/ckipp/Documents/scala-workspace/be-scala-talk/src/.scala-build/?id\u003dproject_183d125c5c"
    }
  ]
}

[Trace - 00:50:15 PM] Received response 'buildTarget/scalaMainClasses - (13)' in 3ms
Result: {
  "items": [
    {
      "target": {
        "uri": "file:/Users/ckipp/Documents/scala-workspace/be-scala-talk/src/.scala-build/?id\u003dproject_183d125c5c"
      },
      "classes": [
        {
          "class": "example.hello",
          "arguments": [],
          "jvmOptions": [],
          "environmentVariables": []
        }
      ]
    }
  ]
}
Error: null
```

---

# How does it even know what the main method is?
##  SemanticDB


> SemanticDB is a data model for semantic information such as symbols and types
> about programs in Scala and other languages. SemanticDB decouples production
> and consumption of semantic information, establishing documented means for
> communication between tools.

- In Scala 2, this is added as a compiler plugin to your build so when your
    build server compiles your code, it produces semanticDB
- In Scala 3, the compile as the ability to nativity be told to produce
    semanticDB if asked to.
- Metals utilizes this for code navigation, plus some other goodies


---

# How does it even know what the main method is?
##  SemanticDB

We can look at the current file, and see if the symbol occurrence is there.
```
Main.scala
----------

Summary:
Schema => SemanticDB v4
Uri => Main.scala
Text => empty
Language => Scala
Symbols => 5 entries
Occurrences => 10 entries

Symbols:
example/Main$package. => final package object example extends Object { self: example.type => +3 decls }
example/Main$package.greeting(). => method greeting(who: String): String
example/Main$package.greeting().(who) => param who: String
example/Main$package.hello(). => @main method hello(): Unit
example/hello# => final class hello extends Object { self: hello => +2 decls }

Occurrences:
[2:8..2:15) <= example/
[4:4..4:12) <= example/Main$package.greeting().
[4:13..4:16) <= example/Main$package.greeting().(who)
[4:18..4:24) => scala/Predef.String#
[4:43..4:46) => example/Main$package.greeting().(who)
[4:47..4:48) => scala/StringContext#s().
[6:1..6:5) => scala/main#
[6:10..6:15) <= example/Main$package.hello().
[7:2..7:9) => scala/Predef.println(+1).
[7:10..7:18) => example/Main$package.greeting().
```

---

# How does it even know what the main method is?
## LSP - `textDocument/codeLens` 

```json
[Trace - 02:31:34 PM] Sending response 'textDocument/codeLens - (3)'. Processing request took 3ms
Result: [
  {
    "range": {...},
    "command": {
      "title": "run",
      "command": "metals-run-session-start",
      "arguments": [
        {
          "targets": [
            {
              "uri": "file:/Users/ckipp/Documents/scala-workspace/be-scala-talk/src/.scala-build/?id\u003dproject_183d125c5c"
            }
          ],
          "dataKind": "scala-main-class",
          "data": {
            "class": "example.hello",
            "arguments": [],
            "jvmOptions": [],
            "environmentVariables": []
          }
        }
      ]
    }
  },
  ...
]
```
---

# Some questions you might have

> ~~How does it even know what the main method is~~

> How does it know to debug or just run?

> How does your debug client know where your debug server is?

> What does the DAP communication actually look like?

> What if I think code lenses are dumb and don't want to use them?

---
# How does it know to debug or just run?
## Client Commands

_This part will differ a bit by client_

```lua
-- commands table
commands["metals-run-session-start"] = debug_start_command(true)

local function debug_start_command(no_debug)
  return function(cmd, _)
    -- dap is the client package implementing DAP
    dap.run({
      type = "scala",
      -- launch vs attach
      request = "launch",
      -- will be thrown away later
      name = "from_lens",
      -- to debug or not
      noDebug = no_debug,
      -- any commands from the code lens
      metals = cmd.arguments,
    })
  end
end

```


---

# How does it know to debug or just run?
## Client Commands


```lua
  dap.adapters.scala = function(callback, config)
    local uri = vim.uri_from_bufnr(0)
    local arguments = {}

    if config.name == "from_lens" then
      arguments = config.metals
    else
      local metals_dap_settings = config.metals or {}

      arguments = {
        path = uri,
        runType = metals_dap_settings.runType or "run",
        args = metals_dap_settings.args,
        jvmOptions = metals_dap_settings.jvmOptions,
        env = metals_dap_settings.env,
        envFile = metals_dap_settings.envFile,
      }
    end

    -- sent to Metals
    execute_command({
      command = "metals.debug-adapter-start",
      arguments = arguments,
    }, function(_, _, res)
      if res then
        local port = util.split_on(res.uri, ":")[3]

        callback({
          type = "server",
          host = "127.0.0.1",
          port = port,
          options = { ... },
          enrich_config = function(_config, on_config) ... end,
        })
      end
    end)
  end

```

---

# How does it know to debug or just run?
## LSP - `workspace/executeCommand`


```json
[Trace - 02:56:12 PM] Received request 'workspace/executeCommand - (12)'
Params: {
  "command": "metals.debug-adapter-start",
  "arguments": [
    {
      "dataKind": "scala-main-class",
      "data": {
        "class": "example.hello",
        "environmentVariables": [],
        "arguments": [],
        "jvmOptions": []
      },
      "targets": [
        {
          "uri": "file:/Users/ckipp/Documents/scala-workspace/be-scala-talk/src/.scala-build/?id\u003dproject_183d125c5c"
        }
      ]
    }
  ]
}
```

---

# How does your debug client know where your debug server is?
## BSP - `debugSessionStart`

```json
[Trace - 03:19:07 PM] Sending request 'debugSession/start - (16)'
Params: {
  "targets": [
    {
      "uri": "file:/Users/ckipp/Documents/scala-workspace/be-scala-talk/src/.scala-build/?id\u003dproject_183d125c5c"
    }
  ],
  "dataKind": "scala-main-class",
  "data": {
    "class": "example.hello",
    "arguments": [],
    "jvmOptions": [],
    "environmentVariables": []
  }
}


[Trace - 03:49:47 PM] Received response 'debugSession/start - (16)' in 1286ms
Result: {
  "uri": "tcp://0.0.0.0:63167"
}
```

At this point, your build server launches a debug server via
`scalacenter/scala-debug-adapter`.

This project is utilized by both Bloop and
sbt in order for the server to implement DAP.

---

# How does your debug client know where your debug server is?
## LSP - `workspace/executeCommand`

```json
[Trace - 02:56:13 PM] Sending response 'workspace/executeCommand - (12)'. Processing request took 906ms
Result: {
  "name": "example.hello",
  "uri": "tcp://127.0.0.1:63167"
}
```

Now the DAP client has this information and itself is able to start
communicating with the server via DAP.

---

# Some questions you might have

> ~~How does it even know what the main method is~~

> ~~How does it know to debug or just run?~~

> ~~How does your debug client know where your debug server is?~~

> What does the DAP communication actually look like?

> What if I think code lenses are dumb and don't want to use them?

---

# What does the DAP communication actually look like?
## The initialization process

```json
[Trace][03:53:57 PM] Sent request:
{
  "type": "request",
  "seq": 1,
  "command": "initialize",
  "arguments": {
    "clientId": "neovim",
    "clientname": "neovim",
    "adapterID": "nvim-dap",
    "supportsRunInTerminalRequest": true,
    ...
  }
}

[Trace][03:53:58 PM] Received response:
{
  "type": "response",
  "seq": 1,
  "request_seq": 1,
  "command": "initialize",
  "success": true,
  "body": {
    "supportsHitConditionalBreakpoints": true,
    ...
  }
}

```

---

# The actual DAP communication
## The actual launch request

```json
[Trace][03:53:58 PM] Sent request:
{
  "type": "request",
  "seq": 2,
  "command": "launch",
  "arguments": {
    "noDebug": true,
    "request": "launch",
    "name": "from_lens",
    "type": "scala"
  }
}

```

And then following this launch, would be all the other information about
breakpoints, logging, etc untill the process has fished.

---


# Some questions you might have

> ~~How does it even know what the main method is~~

> ~~How does it know to debug or just run?~~

> ~~How does your debug client know where your debug server is?~~

> ~~What does the DAP communication actually look like?~~

> What if I think code lenses are dumb and don't want to use them?

---

# What if I think code lenses are dumb and don't want to use them?
## Local settings

Difference clients will handle this slightly different, but all using "Debug
Discovery" as we call it in Metals.

```lua
 dap.configurations.scala = {
   {
     type = "scala",
     request = "launch",
     name = "Run or Test",
     metals = {
       runType = "runOrTestFile"
       args = "..."
     },
   },
   ...
 }
```

Or in other clients this would be put in your `launch.json`.

---

# What if I think code lenses are dumb and don't want to use them?
## LSP - `workspace/executeCommand`

```json
[Trace - 11:55:36 AM] Received request 'workspace/executeCommand - (25)'
Params: {
  "command": "metals.debug-adapter-start",
  "arguments": [
    {
      "path": "file:///Users/ckipp/Documents/scala-workspace/be-scala-talk/src/Main.scala",
      "runType": "runOrTestFile"
    }
  ]
}
```

Just like before, but now _only_ with the `runType` and `path` defined.

If there is only one main or test, then it will just automatically start up the
debugger and run it.

---

# Some questions you might have

> ~~How does it even know what the main method is~~

> ~~How does it know to debug or just run?~~

> ~~How does your debug client know where your debug server is?~~

> ~~What does the DAP communication actually look like?~~

> ~~What if I think code lenses are dumb and don't want to use them?~~

---

# Test Explorer

- Currently only exists in VS Code
- Great way to run individual tests (for some frameworks)
- Great way to re-run certain chunks of tests
- Behind the scenes a more similiar process to what we have outlined, with some
    extras.
- Let's see a demo of this


---

# Any questions?

```
  ___   ___   ___ 
 |__ \ |__ \ |__ \
   / /   / /   / /
  |_|   |_|   |_| 
  (_)   (_)   (_) 
```

---

# Thanks!

- [This talk was inspired by my blog post](https://www.chris-kipp.io/blog/the-debug-adapter-protocol-and-scala)
- [Metals website](https://scalameta.org/metals/)
- [nvim-metals on GitHub](https://github.com/scalameta/nvim-metals)
- [nvim-dap on GitHub](https://github.com/mfussenegger/nvim-dap)
- [scala-debug-adapter on GitHub](https://github.com/scalacenter/scala-debug-adapter)
- [LSP site](https://microsoft.github.io/language-server-protocol/)
- [BSP site](https://build-server-protocol.github.io/)
- [DAP site](https://microsoft.github.io/debug-adapter-protocol/overview)
- [A Dive into how Metals works talk](https://youtu.be/fpzN_vTBy18)
