import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'calciatore',
        loadChildren: './calciatore/calciatore.module#NextrainingCalciatoreModule'
      },
      {
        path: 'testdi-conconi',
        loadChildren: './testdi-conconi/testdi-conconi.module#NextrainingTestdiConconiModule'
      },
      {
        path: 'testdi-cooper',
        loadChildren: './testdi-cooper/testdi-cooper.module#NextrainingTestdiCooperModule'
      },
      {
        path: 'parametri-fisici',
        loadChildren: './parametri-fisici/parametri-fisici.module#NextrainingParametriFisiciModule'
      },
      {
        path: 'prova-1500-m',
        loadChildren: './prova-1500-m/prova-1500-m.module#NextrainingProva1500mModule'
      },
      {
        path: 'prova-70-m',
        loadChildren: './prova-70-m/prova-70-m.module#NextrainingProva70mModule'
      },
      {
        path: 'infortunio',
        loadChildren: './infortunio/infortunio.module#NextrainingInfortunioModule'
      },
      {
        path: 'specialista',
        loadChildren: './specialista/specialista.module#NextrainingSpecialistaModule'
      },
      {
        path: 'allenamento',
        loadChildren: './allenamento/allenamento.module#NextrainingAllenamentoModule'
      },
      {
        path: 'allenamento-aggiuntivo',
        loadChildren: './allenamento-aggiuntivo/allenamento-aggiuntivo.module#NextrainingAllenamentoAggiuntivoModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NextrainingEntityModule {}
