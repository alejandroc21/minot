import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Tag } from '@models/tag';

@Component({
  selector: 'app-tag-item',
  standalone: true,
  imports: [],
  templateUrl: './tag-item.component.html',
  styleUrl: './tag-item.component.css'
})
export class TagItemComponent {
  @Input() tag!:Tag;
  @Output() close = new EventEmitter();
  
  onClose(){
    this.close.emit();
  }
}
