import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-delete-modal',
  templateUrl: './delete-modal.component.html',
  styleUrls: ['./delete-modal.component.css']
})
export class DeleteModalComponent {
  @Output() deleteConfirmed = new EventEmitter<void>();

  openModal(): void {
    const modal = document.getElementById('delete_modal') as HTMLDialogElement;
    modal.showModal();
  }

  closeModal(): void {
    const modal = document.getElementById('delete_modal') as HTMLDialogElement;
    modal.close();
  }

  confirmDelete(): void {
    this.deleteConfirmed.emit();
    this.closeModal();
  }
}
