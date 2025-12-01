import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'bembos-ranking',
  template: `
    <section class="container">
      <h2>Ranking de la comunidad</h2>
      <p>Hamburguesas más votadas (mock).</p>
      <div class="card" *ngFor="let b of burgers">
        <h4>{{b.name}} <small style="color:rgba(255,255,255,0.8)">({{b.votes}} votos)</small></h4>
        <p style="margin:0">{{b.desc}}</p>
      </div>
    </section>
  `,
  standalone: true,
  imports: [CommonModule]
})
export class RankingComponent {
  burgers = [
    { name: 'La Clásica', votes: 124, desc: 'Queso, tomate, lechuga.' },
    { name: 'Volcano', votes: 98, desc: 'Extra picante con salsa BBQ.' }
  ];
}
