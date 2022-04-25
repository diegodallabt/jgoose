# JGOOSE CONTRIBUTING 
## <a name="commit"></a> Boas práticas para mensagem de commit

Mensagens de commit devem ser **legíveis** e de fácil entendimento. Assim as mensagens de para commit de qualque branch na JGOOSE devem seguir um **padrão**.

### Formato Commit Message 

Cada mensagem consiste de um formato inclue um **tipo**, um **escopo** e um **assunto**.

```
<tipo>(<escopo>): <assunto>
```

O **tipo** é obrigatório e o escopo é **opcional**, o **assunto** é obrigatório também.

A mensagem não deve ser maior que 100 caracteres! Isso permite que a mensagem seja mais fácil de ler no Github.

### Tipos

Deve ser um dos seguintes:

* **build**: Mudanças que afetam o sistema de build ou dependências externas (Exemplo: maven)
* **docs**: Mudanças na documentação (Exemplos: novos diagramas de classe, pacotes)
* **feat**: Uma nova feature (Exemplo: nova funcionalidade)
* **fix**: Corrijido um bug 
* **perf**: Uma mundaça que melhora o desempenho 
* **refactor**: Uma mudança que não corrije um bug ou adiciona uma feature
* **style**: Mudanças que não afetam a semântica e a sintaxe do código (espaços, formtação, ponto e vírgulas, etc)
* **test**: Adicionado um teste ou corrijido um existente

Caso exista um sobreposição de tipos, use aquele predominante. Para evitar isso, tente separa os commit de forma que cada um só faça uma coisa (_Single Responsability Principle).

### Escopo

O escopo deve conter que módulo ou parte do código foi afetado.

A seguir alguns escopos:
* **bpmn**
* **istar**
* **traceability**
* **maven**
* **config**
* **ux**
* **ui**
* **projects**
* **devExperience**
* **verticalTrace**
* **horizontalTrace**

Caso exista um sobreposição de escopo, use o predominante. Tente separa os commit de forma que cada um só faça uma coisa (_Single Responsability Principle). Caso falte algum crie um PR e adicione ele aqui nesta documentação.

### Assunto

O assunto contém uma descrição sussinta da mudança:

* Não começe com letra maíscula
* Não use um ponto (.) no fim

## Exemplos de commit
```
feat(bpmn): button to get active pools
```
```
fix(ux): color button red to green
```

### Reverter

Se um um commit reverte um anterior, ele deve começar como `revert: `, seguido com um cabeçalho do commit revertido. No corpo deve dizer: `Reverte o commit <hash>.`, onde a hash é a SHA do commit que foi revertido.

*Essa seção é baseada no [Angular](https://github.com/angular/angular/blob/master/CONTRIBUTING.md#commit), possuindo várias alterações visando o escopo da ferramenta JGOOSE*
