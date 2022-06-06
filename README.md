# BeScala Meetup Talk - 09-06-2022

This contains the presentation and minimal code that I use for the [BeScala](https://www.meetup.com/BeScala/) meetup.

## Trying it out

The code is very minimal and [scala-cli](https://scala-cli.virtuslab.org/). The
examples that we go over in the talk are all using
[Metals](https://scalameta.org/metals/). If you'd like to follow along you can
do the following.

  - `git clone git@github.com:ckipp01/be-scala-talk.git`
  - `scala-cli run src` (this will run the app, but also create the `src/.bsp`
      which Metals needs)
  - `nvim src/Main.scala` or using whichever editor is your poison that supports
      debugging with Metals
  - After you see the `src/.metals` created create the trace files which is what
      we'll look at during the talk

```
touch $WORKSPACE/.metals/lsp.trace.json
touch $WORKSPACE/.metals/bsp.trace.json
touch $WORKSPACE/.metals/dap-server.trace.json
touch $WORKSPACE/.metals/dap-client.trace.json
```

  - Close and re-open again to start getting logs piped to the trace files
  - Try out the code lenses to run the main method in `src/Main.scala`

## The presentation

The presentation is located in `presentation.md` and is given using
[slides](https://github.com/maaslalani/slides). If you have slides installed,
you can just `slides presentation.md` and it will appear in your terminal.
