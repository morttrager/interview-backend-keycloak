import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InterviewdbSharedModule } from 'app/shared/shared.module';
import { QuestionSetTagComponent } from './question-set-tag.component';
import { QuestionSetTagDetailComponent } from './question-set-tag-detail.component';
import { QuestionSetTagUpdateComponent } from './question-set-tag-update.component';
import { QuestionSetTagDeleteDialogComponent } from './question-set-tag-delete-dialog.component';
import { questionSetTagRoute } from './question-set-tag.route';

@NgModule({
  imports: [InterviewdbSharedModule, RouterModule.forChild(questionSetTagRoute)],
  declarations: [
    QuestionSetTagComponent,
    QuestionSetTagDetailComponent,
    QuestionSetTagUpdateComponent,
    QuestionSetTagDeleteDialogComponent,
  ],
  entryComponents: [QuestionSetTagDeleteDialogComponent],
})
export class InterviewdbQuestionSetTagModule {}
