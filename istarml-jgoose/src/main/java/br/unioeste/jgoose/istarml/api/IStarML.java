package br.unioeste.jgoose.istarml.api;

import java.util.List;

public abstract interface IStarML
{
  public abstract IDiagram createDiagram();
  
  public abstract boolean removeDiagram(IDiagram paramIDiagram);
  
  public abstract List<IDiagram> getAllDiagrams();
  
  public abstract IDiagram createDiagram(String paramString);
  
  public abstract IDiagram getDiagramById(String paramString);
  
  public abstract IDiagram getDiagramByName(String paramString);
  
  public abstract boolean addActor(IActor paramIActor);
  
  public abstract boolean addActor(String paramString);
  
  public abstract boolean addActor(String paramString1, String paramString2);
  
  public abstract IActor createActor();
  
  public abstract boolean removeActor(IActor paramIActor);
  
  public abstract List<IActor> getAllActors();
  
  public abstract IActor createAgent();
  
  public abstract IActor createRole();
  
  public abstract IActor createPosition();
  
  public abstract boolean changeToAgent(IActor paramIActor);
  
  public abstract boolean changeToRole(IActor paramIActor);
  
  public abstract boolean changeToPosition(IActor paramIActor);
  
  public abstract IIntentionalElement createIntentionalElement();
  
  public abstract boolean removeIntentionalElement(IIntentionalElement paramIIntentionalElement);
  
  public abstract List<IIntentionalElement> getAllIntentionalElements();
  
  public abstract IIntentionalElement createGoal();
  
  public abstract IIntentionalElement createSoftGoal();
  
  public abstract IIntentionalElement createTask();
  
  public abstract IIntentionalElement createResource();
  
  public abstract boolean connect(IIntentionalElement paramIIntentionalElement1, IIntentionalElement paramIIntentionalElement2);
  
  public abstract boolean connect(IIntentionalElement paramIIntentionalElement, IIntentionalElement... paramVarArgs);
  
  public abstract boolean removeConnection(IIntentionalElement paramIIntentionalElement1, IIntentionalElement paramIIntentionalElement2);
  
  public abstract List<IIntentionalElement> getAllOutConnections(IIntentionalElement paramIIntentionalElement);
  
  public abstract List<IIntentionalElement> getAllInConnections(IIntentionalElement paramIIntentionalElement);
  
  public abstract void makeDependency();
  
  public abstract void checkGuidelines();
}


