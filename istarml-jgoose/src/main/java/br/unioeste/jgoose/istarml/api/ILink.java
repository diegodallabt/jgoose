package br.unioeste.jgoose.istarml.api;

public abstract interface ILink
{
  public static abstract interface IContribution
    extends ILink
  {}
  
  public static abstract interface IMeansEnd
    extends ILink
  {}
  
  public static abstract interface IDecomposition
    extends ILink
  {}
  
  public static abstract interface IDependency
    extends ILink
  {}
  
  public static abstract interface IActorIns
    extends ILink.IActorLink
  {}
  
  public static abstract interface IActorOccupies
    extends ILink.IActorLink
  {}
  
  public static abstract interface IActorPlays
    extends ILink.IActorLink
  {}
  
  public static abstract interface IActorCovers
    extends ILink.IActorLink
  {}
  
  public static abstract interface IActorIsPartOF
    extends ILink.IActorLink
  {}
  
  public static abstract interface IActorIsA
    extends ILink.IActorLink
  {}
  
  public static abstract interface IActorLink
    extends ILink
  {}
}
