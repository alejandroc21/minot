import { Component, effect, inject, OnInit, signal } from '@angular/core';
import { ItemService } from '../../../item/services/item.service';
import { NoteCardComponent } from "../note-card/note-card.component";
import { ModalService } from '../../../modal/service/modal.service';


@Component({
  selector: 'app-note',
  standalone: true,
  imports: [NoteCardComponent],
  templateUrl: './note.component.html',
  styleUrl: './note.component.css'
})
export default class NoteComponent implements OnInit{
  private _itemService = inject(ItemService);
  private _modalService = inject(ModalService);
  items = this._itemService.items;
  viewGrid = true;
  sortAsc = true;

  ngOnInit(): void {
      this._itemService.listItems().subscribe();
  }

  showGrid(){
    this.viewGrid = true;
  }
  showList(){
    this.viewGrid = false;
  }

  toggleSort(){
    this.sortAsc = !this.sortAsc;
  }


  createFolder(){
    this._modalService.input("Inserta un nombre", "Nueva carpeta", "");
  }

  showAlert(){
    this._modalService.alert("mensaje medianamente largo");
  }

  showConfirm(){
    this._modalService.comfirm("confirma esto");
  }
}
