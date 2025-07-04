import { Component, input } from '@angular/core';
import { Item, ItemType, Status } from '../../model/item';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-item-card',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './item-card.component.html',
  styleUrl: './item-card.component.css',
})
export class ItemCardComponent {
  item = input.required<Item>();
  grid = input.required<boolean>();

  isTask() {
    if (this.item().type && this.item().type === ItemType.TASK) {
      return true;
    }
    return false;
  }

  color() {
    return this.item().status;
  }

  getRelativeDate() {
    const date = this.item().updatedAt!;
    const diffSeconds = Math.floor(
      (new Date().getTime() - new Date(date).getTime()) / 1000
    );

    const MINUTE = 60;
    const HOUR = 3600;
    const DAY = 84600;

    if (diffSeconds < MINUTE) {
      return `${diffSeconds} segundos`;
    } else if(diffSeconds < HOUR){
      const minutes = Math.floor(diffSeconds/MINUTE);
      return `${minutes} ${minutes === 1 ? 'minuto': 'minutos'}`;
    }else if(diffSeconds<DAY){
        return new Date(date).toLocaleTimeString('es-CO',{hour:'2-digit', minute:'2-digit'});
    }
    return new Date(date).toLocaleDateString('es-CO',{ hour:'2-digit', minute:'2-digit',day:'numeric', month:'2-digit', year:'numeric'});
  }

  get status() {
    if (this.item().status === Status.TODO) return 'Pendiente';
    else if (this.item().status === Status.IN_PROGRESS) return 'En progreso';
    else return 'Completado';
  }

  get trashed() {
    return this.item().trashed!;
  }
}
