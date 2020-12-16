import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InterviewdbSharedModule } from 'app/shared/shared.module';
import { QTagComponent } from './q-tag.component';
import { QTagDetailComponent } from './q-tag-detail.component';
import { QTagUpdateComponent } from './q-tag-update.component';
import { QTagDeleteDialogComponent } from './q-tag-delete-dialog.component';
import { qTagRoute } from './q-tag.route';

@NgModule({
  imports: [InterviewdbSharedModule, RouterModule.forChild(qTagRoute)],
  declarations: [QTagComponent, QTagDetailComponent, QTagUpdateComponent, QTagDeleteDialogComponent],
  entryComponents: [QTagDeleteDialogComponent],
})
export class InterviewdbQTagModule {}
