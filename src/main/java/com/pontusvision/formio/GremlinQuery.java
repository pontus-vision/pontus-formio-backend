package com.pontusvision.formio;

import java.util.Map;

public class GremlinQuery
{
  String              gremlin;
  Map<String, Object> bindings;

  public Map<String, Object> getBindings()
  {
    return bindings;
  }

  public void setBindings(Map<String, Object> bindings)
  {
    this.bindings = bindings;
  }

  public String getGremlin()
  {
    return gremlin;
  }

  public void setGremlin(String gremlin)
  {
    this.gremlin = gremlin;
  }

}


