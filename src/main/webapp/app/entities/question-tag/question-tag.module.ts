import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InterviewdbSharedModule } from 'app/shared/shared.module';
import { QuestionTagComponent } from './question-tag.component';
import { QuestionTagDetailComponent } from './question-tag-detail.component';
import { QuestionTagUpdateComponent } from './question-tag-update.component';
import { QuestionTagDeleteDialogComponent } from './question-tag-delete-dialog.component';
import { questionTagRoute } from './question-tag.route';

@NgModule({
  imports: [InterviewdbSharedModule, RouterModule.forChild(questionTagRoute)],
  declarations: [QuestionTagComponent, QuestionTagDetailComponent, QuestionTagUpdateComponent, QuestionTagDeleteDialogComponent],
  entryComponents: [QuestionTagDeleteDialogComponent],
})
export class InterviewdbQuestionTagModule {}
